package com.example.inventoryTracker.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventoryTracker.DTO.RequestDTOS.AppUserRequestDTO;
import com.example.inventoryTracker.DTO.RequestDTOS.PasswordRequestDTO;
import com.example.inventoryTracker.DTO.ResponseDTOS.AppUserResponseDTO;
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
    public ResponseEntity<AppUserResponseDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok().body(appUserService.getUserById(id));
    }

    @GetMapping("/users")
    public ResponseEntity<List<AppUserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok().body(appUserService.getAllUsers());
    }

    @PostMapping("/users")
    public ResponseEntity<AppUserResponseDTO> createUser(@RequestBody AppUserRequestDTO entity) {
        return ResponseEntity.ok().body(appUserService.createUser(entity));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<AppUserResponseDTO> updateUserInformation(@PathVariable Long id, @RequestBody AppUserRequestDTO entity) {
        return ResponseEntity.ok().body(appUserService.updateUserInformation(id, entity));
    }

    @PutMapping("/users/{id}/password")
    public ResponseEntity<AppUserResponseDTO> updatePassword(@PathVariable Long id, @RequestBody PasswordRequestDTO passwordRequestDTO) {
        return ResponseEntity.ok().body(appUserService.updatePassword(id, passwordRequestDTO.getNewPassword()));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        appUserService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
    
}
