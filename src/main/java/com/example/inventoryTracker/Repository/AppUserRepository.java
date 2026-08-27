package com.example.inventoryTracker.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.inventoryTracker.Entities.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

}
