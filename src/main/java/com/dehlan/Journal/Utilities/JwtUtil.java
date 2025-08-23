package com.dehlan.Journal.Utilities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    // 🔑 Secret key used to sign & validate JWTs
    private static final String SECRET_KEY = "8f3e2b7a4c1d9f0e6a3b8c5d1f7e2a9b8c5d1f7e2a9b8c5d1f7e2a9b8c5d1f7";

    // 📌 Method to generate JWT for a given username
    public String generateToken(String username) {
        log.info("🔑 Generating JWT for user: {}", username);
        return Jwts.builder()
                .setSubject(username) // username will be stored as "subject" inside JWT
                .setIssuedAt(new Date(System.currentTimeMillis())) // when token was created
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // expiry time = 1 hour
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // signing token using HS256 algorithm & secret key
                .compact(); // convert JWT object into a compact String
    }

    // 📌 Method to extract username (subject) from JWT
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject(); // "subject" in JWT payload = username
    }

    // 📌 Method to check if JWT is valid for a given user
    public boolean validateToken(String token, String username) {
        // token is valid if username matches AND token is not expired
        return username.equals(extractUsername(token)) && !isTokenExpired(token);
    }

    // 📌 Method to check if token has expired
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
        // if expiry date is before current time → expired
    }

    // 📌 Utility method to extract all claims (data) from JWT
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY) // use same secret key to validate token
                .parseClaimsJws(token) // parse JWT
                .getBody(); // return claims (payload of JWT)
    }
}

