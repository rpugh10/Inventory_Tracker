package com.example.inventoryTracker.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventoryTracker.DTO.RequestDTOS.LocationRequestDTO;
import com.example.inventoryTracker.DTO.ResponseDTOS.LocationResponseDTO;
import com.example.inventoryTracker.Service.LocationService;

@RestController
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('STAFF')")
    @GetMapping("/locations/{id}")
    public ResponseEntity<LocationResponseDTO> getLocation(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.findLocationById(id));
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('STAFF')")
    @GetMapping("/locations")
    public ResponseEntity<List<LocationResponseDTO>> getAllLocations() {
        return ResponseEntity.ok(locationService.findAllLocations());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/locations")
    public ResponseEntity<LocationResponseDTO> createLocation(@RequestBody LocationRequestDTO locationDTO) {
        return ResponseEntity.ok(locationService.saveLocation(locationDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/locations/{id}")
    public ResponseEntity<LocationResponseDTO> updateLocation(@PathVariable Long id, @RequestBody LocationRequestDTO locationDTO) {
        return ResponseEntity.ok(locationService.updateLocation(id, locationDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/locations/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }
}
