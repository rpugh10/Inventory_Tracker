package com.example.inventoryTracker.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventoryTracker.DTO.AppUserRequestDTO.AppUserDTO;
import com.example.inventoryTracker.Service.AppUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<AppUserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok().body(appUserService.getUserById(id));
    }

    @GetMapping("/users")
    public ResponseEntity<List<AppUserDTO>> getAllUsers() {
        return ResponseEntity.ok().body(appUserService.getAllUsers());
    }

    @PostMapping("/users")
    public ResponseEntity<AppUserDTO> createUser(@RequestBody AppUserDTO entity) {
        return ResponseEntity.ok().body(appUserService.createUser(entity));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<AppUserDTO> updateUser(@PathVariable Long id, @RequestBody AppUserDTO entity) {
        return ResponseEntity.ok().body(appUserService.updateUser(id, entity));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        appUserService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
    
}
