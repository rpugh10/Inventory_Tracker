package com.example.inventoryTracker.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventoryTracker.DTO.RequestDTOS.UserRequestDTOS.AppUserLoginDTO;
import com.example.inventoryTracker.Utility.JWTUtility;

@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JWTUtility jwtUtility;

    public AuthController(AuthenticationManager authenticationManager, JWTUtility jwtUtility) {
        this.authenticationManager = authenticationManager;
        this.jwtUtility = jwtUtility;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AppUserLoginDTO entity) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(entity.getUsername(), entity.getPassword());
        authenticationManager.authenticate(authenticationToken); // Authenticate the user credentials
        return ResponseEntity.ok().body(jwtUtility.generateToken(entity.getUsername())); // Generate and return a JWT token
    }
}
