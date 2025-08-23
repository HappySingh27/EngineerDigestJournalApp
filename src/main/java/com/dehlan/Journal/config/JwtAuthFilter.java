package com.dehlan.Journal.config;

import com.dehlan.Journal.Utilities.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;


    // 🔧 Constructor injection of JwtUtil & UserDetailsService
    public JwtAuthFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        log.info("🛡️ JwtAuthFilter created and registered in filter chain");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        log.debug("➡️ JwtAuthFilter triggered for URI: {}", request.getRequestURI());

        // 1️⃣ Get Authorization header from request
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        // 2️⃣ Check if header starts with "Bearer <token>"
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); // extract token after "Bearer "
            username = jwtUtil.extractUsername(token); // get username from token
        }

        // 3️⃣ If we got username AND authentication is not already set
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Load user details from DB/service
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Validate token against user details
            if (jwtUtil.validateToken(token, userDetails.getUsername())) {
                // Create authentication object
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // Set authentication in Spring Security Context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        log.info("➡️ Passing request forward in JwtAuthFilter: {}", request.getRequestURI());
        // 4️⃣ Continue filter chain
        filterChain.doFilter(request, response);
    }
}

