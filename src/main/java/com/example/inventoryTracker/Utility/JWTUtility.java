package com.example.inventoryTracker.Utility;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTUtility {

    @Value("${jwt.secret}")
    private String secret;

    /**
     * Generates a JWT token for the given username.
     *
     * @param username the username to include in the token
     * @return the generated JWT token
     */
    public String generateToken(String username){
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); // Creates a secret key from the provided secret string
        Instant now = Instant.now(); //Gets the time

        return Jwts.builder()
                .subject(username) // Sets the subject of the token (the username)
                .issuer("inventoryTracker") // Sets the issuer of the token
                .issuedAt(java.util.Date.from(now)) // Token issue time
                .expiration(java.util.Date.from(now.plusSeconds(3600))) // Token valid for 1 hour
                .signWith(key) // Signs the token with the secret key
                .compact(); // Builds the token and returns it as a string
    }

    /**
     * Extracts the username from the given JWT token.
     *
     * @param token the JWT token
     * @return the username extracted from the token
     */
    public String extractUsername(String token){
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        Claims claims = Jwts.parser() // I want to read and process the JWT token
                .verifyWith(key) // Verifies the token signature with the secret key
                .build() // Builds the JWT parser
                .parseSignedClaims(token) // Parses the token and retrieves the claims
                .getPayload(); // Retrieves the payload (claims) from the parsed token

        return claims.getSubject(); // Returns the subject (username) from the token claims
    }

    /**
     * Validates the given JWT token and returns the claims if valid.
     *
     * @param token the JWT token to validate
     * @return the claims extracted from the token if valid
     * @throws RuntimeException if the token is invalid
     */
    public Claims validateToken(String token){
        try{
                SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                Jws<Claims> claimsJws = Jwts.parser() // I want to read and process the JWT token
                    .verifyWith(key) // Verifies the token signature with the secret key
                    .requireIssuer("inventoryTracker") // Ensures the token was issued by the expected issuer
                    .build() // Builds the JWT parser
                    .parseSignedClaims(token); // Parses the token and retrieves the claims
            return claimsJws.getPayload(); // Retrieves the payload (claims) from the parsed token
        }catch(JwtException e){
            // Handle invalid token
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

    
}
