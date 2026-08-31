package com.example.inventoryTracker.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.inventoryTracker.DTO.RequestDTOS.AppUserRequestDTO;
import com.example.inventoryTracker.DTO.ResponseDTOS.AppUserResponseDTO;
import com.example.inventoryTracker.Entities.AppUser;
import com.example.inventoryTracker.Mapper.AppUserMapper;
import com.example.inventoryTracker.Repository.AppUserRepository;

@Service
public class AppUserService {

    private final AppUserMapper appUserMapper;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUserService(AppUserMapper appUserMapper, AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserMapper = appUserMapper;
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AppUserResponseDTO getUserById(Long id){
        return appUserRepository.findById(id)
                .map(appUserMapper:: toAppUserDTO)
                .orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
    }

    public List<AppUserResponseDTO> getAllUsers(){
        return appUserRepository.findAll().stream()
                .map(appUserMapper:: toAppUserDTO)
                .toList();
    }

    public AppUserResponseDTO createUser(AppUserRequestDTO userDTO){
        AppUser user = appUserMapper.toAppUser(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        AppUser savedUser = appUserRepository.save(user);
        return appUserMapper.toAppUserDTO(savedUser);
    }

    public AppUserResponseDTO updateUserInformation(Long id, AppUserRequestDTO userDTO){
        Optional<AppUser> user = appUserRepository.findById(id); //Here we have to do Optional<AppUser> because findById() returns that.
        AppUser newUser = user.orElseThrow(() -> new RuntimeException("User not found"));
        newUser.setUsername(userDTO.getUsername());
        newUser.setEmail(userDTO.getEmail());
        newUser.setRole(userDTO.getRole());
        AppUser updatedUser = appUserRepository.save(newUser);
        return appUserMapper.toAppUserDTO(updatedUser);
         
    }

    public AppUserResponseDTO updatePassword(Long id, String newPassword){
        Optional<AppUser> user = appUserRepository.findById(id);
        AppUser existingUser = user.orElseThrow(() -> new UsernameNotFoundException("User not found"));
        existingUser.setPassword(passwordEncoder.encode(newPassword));
        AppUser updatedUser = appUserRepository.save(existingUser);
        return appUserMapper.toAppUserDTO(updatedUser);
    }

    public void deleteUser(Long id){
        Optional<AppUser> user = appUserRepository.findById(id);
        if (user.isPresent()) {
            appUserRepository.deleteById(id);
        } else {
            throw new UsernameNotFoundException("User not found with id: " + id);
        }
    }
}
