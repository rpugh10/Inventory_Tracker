package com.example.inventoryTracker.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.inventoryTracker.Entities.AppUser;
import com.example.inventoryTracker.Entities.Enums.Roles;
import com.example.inventoryTracker.Repository.AppUserRepository;

@Configuration 
public class DataInitializer {

    @Value ("${admin.username}")
    private String adminUsername;

    @Value ("${admin.password}")
    private String adminPassword;

    @Bean 
    CommandLineRunner createFirstAdmin(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if(appUserRepository.findByUsername("adminUsername").isEmpty()) {
                AppUser admin = new AppUser();
                admin.setUsername(adminUsername);
                admin.setEmail("admin@example.com");
                admin.setPassword(passwordEncoder.encode(adminPassword));
                admin.setRole(Roles.ADMIN);
                appUserRepository.save(admin);
            }
        };
    }

}
