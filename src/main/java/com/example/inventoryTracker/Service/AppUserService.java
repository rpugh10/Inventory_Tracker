package com.example.inventoryTracker.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.inventoryTracker.DTO.AppUserRequestDTO.AppUserDTO;
import com.example.inventoryTracker.Entities.AppUser;
import com.example.inventoryTracker.Mapper.AppUserMapper;
import com.example.inventoryTracker.Repository.AppUserRepository;

@Service
public class AppUserService {

    private final AppUserMapper appUserMapper;
    private final AppUserRepository appUserRepository;

    public AppUserService(AppUserMapper appUserMapper, AppUserRepository appUserRepository) {
        this.appUserMapper = appUserMapper;
        this.appUserRepository = appUserRepository;
    }

    public AppUserDTO getUserById(Long id){
        return appUserRepository.findById(id)
                .map(appUserMapper:: toAppUserDTO)
                .orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
    }

    public List<AppUserDTO> getAllUsers(){
        return appUserRepository.findAll().stream()
                .map(appUserMapper:: toAppUserDTO)
                .toList();
    }

    public AppUserDTO createUser(AppUserDTO userDTO){
        AppUser user = appUserMapper.toAppUser(userDTO);
        AppUser savedUser = appUserRepository.save(user);
        return appUserMapper.toAppUserDTO(savedUser);
    }

    public AppUserDTO updateUser(Long id, AppUserDTO userDTO){
        Optional<AppUser> user = appUserRepository.findById(id); //Here we have to do Optional<AppUser> because findById() returns that.
        AppUser newUser = user.orElseThrow(() -> new RuntimeException("User not found"));
        newUser.setUsername(userDTO.getUsername());
        newUser.setPassword(userDTO.getPassword());
        newUser.setEmail(userDTO.getEmail());
        newUser.setRole(userDTO.getRole());
        AppUser updatedUser = appUserRepository.save(newUser);
        return appUserMapper.toAppUserDTO(updatedUser);
         
    }

    public void deleteUser(Long id){
        Optional<AppUser> user = appUserRepository.findById(id);
        if (user.isPresent()) {
            appUserRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }
}
