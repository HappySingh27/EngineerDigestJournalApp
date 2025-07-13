/*
 * @EnableWebSecurity enables Spring Security's web security support
 * and registers the Spring Security filter chain used to secure HTTP requests.
 * It is required when you want to customize security behavior using SecurityFilterChain.
 * Internally, it loads WebSecurityConfiguration which sets up security filters.
 *
 * SecurityFilterChain is a Spring bean that defines your custom security rules.
 * It tells Spring which requests to allow or restrict, and what kind of authentication is required.
 * It replaces the older WebSecurityConfigurerAdapter class, which is now removed in Spring Security 6.
 *
 * Inside the SecurityFilterChain method, we configure security rules using HttpSecurity.
 * Calling http.build() returns a SecurityFilterChain object,
 * which Spring uses to apply security filters to all incoming HTTP requests.
 *
 * Summary:
 * 1. @EnableWebSecurity activates Spring Security for the application.
 * 2. SecurityFilterChain defines the actual security configuration.
 * 3. Spring uses the returned SecurityFilterChain bean to secure the application.
 */

/*
 *
 * CSRF stands for Cross-Site Request Forgery.
 * It is an attack where unauthorized commands are sent from a trusted user’s browser.
 * Spring Security enables CSRF protection by default to prevent such attacks on state-changing requests (POST, PUT, DELETE).
 * It works by requiring a CSRF token to be present in each modifying request.
 * For stateless REST APIs, CSRF can be safely disabled using .csrf(csrf -> csrf.disable()).
 *
 * Stateless REST APIs do not store any session or user state on the server.
 * Each request must contain all necessary authentication data (e.g., token, credentials).
 * This is scalable and aligns with REST principles — commonly used with JWT tokens.

 * Stateful REST APIs maintain user session data on the server (e.g., using HttpSession).
 * The server remembers who the user is across multiple requests.
 * This approach is easier for traditional web apps but less scalable for APIs.
 *
 */

/*
 *
 * High-Level Authentication Flow in Spring Security:
 *
 * 1. User sends an HTTP request to a secured endpoint (e.g., /journal/create).
 *
 * 2. Spring Security intercepts the request using the filter chain (configured via SecurityFilterChain).
 *
 * 3. It checks if the endpoint is secured.
 *    - If yes, authentication is required.
 *
 * 4. Spring extracts credentials from the request:
 *    - From form data (formLogin)
 *    - Or Authorization header (Basic Auth)
 *    - Or a token (JWT-based auth)
 *
 * 5. Spring wraps credentials in an Authentication object (e.g., UsernamePasswordAuthenticationToken).
 *
 * 6. This object is passed to the AuthenticationManager.
 *
 * 7. AuthenticationManager calls your UserDetailsService.loadUserByUsername().
 *    - This loads user data (username, encoded password, roles) from DB.
 *
 * 8. Spring compares the raw password from request with the encoded one from DB using PasswordEncoder.matches().
 *
 * 9. If valid:
 *    - Authentication is successful.
 *    - User is stored in SecurityContextHolder.
 *    - Request is forwarded to your controller.
 *
 * 10. If invalid:
 *    - Spring rejects the request with 401 Unauthorized or shows the login page.
 *
 */




package com.dehlan.Journal.config;

import com.dehlan.Journal.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers("/admin**").hasRole("admin")
                        .anyRequest().authenticated()
                ).httpBasic(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    /*
     *
     * AuthenticationManager Bean:
     * - Exposes Spring Security's internal AuthenticationManager as a Spring Bean.
     * - This allows other components (e.g., custom login controller or JWT filters) to use it manually.
     * - Delegates authentication logic to the configured AuthenticationProvider.
     * - Mandatory in Spring Security 6+ if you want to inject AuthenticationManager manually.
     *
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /*
     *
     * AuthenticationProvider Bean (DaoAuthenticationProvider):
     * - Configures how user authentication should be performed.
     * - Uses your custom UserDetailsService to load user details from the database.
     * - Uses PasswordEncoder to compare raw and encoded passwords.
     * - Without this bean, Spring may not know how to authenticate using your DB-stored users.
     * - DaoAuthenticationProvider is commonly used for username/password-based login.
     *
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
        
    }
}
