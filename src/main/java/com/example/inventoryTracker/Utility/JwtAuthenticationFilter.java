package com.example.inventoryTracker.Utility;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.inventoryTracker.Service.AppUserDetailsService;

import io.jsonwebtoken.Claims;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final AppUserDetailsService userDetailsService;
    private final JWTUtility jwtUtility;

    public JwtAuthenticationFilter(AppUserDetailsService userDetailsService, JWTUtility jwtUtility) {
        this.userDetailsService = userDetailsService;
        this.jwtUtility = jwtUtility;
    }

    /**
     * This method is called for each incoming HTTP request. It checks for the presence of a JWT token in the Authorization header,
     * validates it, and sets the authentication in the security context if the token is valid.
     *
     * @param request The incoming HTTP request
     * @param response The outgoing HTTP response
     * @param filterChain The filter chain to pass the request and response to the next filter
     * @throws ServletException If an error occurs during filtering
     * @throws IOException If an I/O error occurs during filtering
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String authorizationHeader = request.getHeader("Authorization"); //Get the authorization header from the request
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) { // Check if the header is not null and starts with "Bearer "
                String token = authorizationHeader.substring(7); //Remove the "Bearer " prefix to get the actual token
                Claims claims = jwtUtility.validateToken(token); // Validate the token and extract claims
                String username = claims.getSubject(); // Extract the username from the claims
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) { // Check if the username is not null and there is no existing authentication in the security context

                    UserDetails userDetails = userDetailsService.loadUserByUsername(username); //Get the user info from the DB

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()); // Create an authentication token with the user details and authorities
                    SecurityContextHolder.getContext().setAuthentication(authentication); // Set the authentication in the security context to indicate that the user is authenticated
                } 
                    
            }
        } catch (Exception e) {
            // Handle any exceptions that may occur during token extraction or validation
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
            return; // Stop further processing of the request if the token is invalid or expired
        }
        filterChain.doFilter(request, response); // Continue the filter chain to allow the request to proceed to the next filter or endpoint
    }
}


