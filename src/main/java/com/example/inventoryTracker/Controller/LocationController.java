package com.example.inventoryTracker.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventoryTracker.DTO.LocationDTO;
import com.example.inventoryTracker.Service.LocationService;

@RestController
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/locations/{id}")
    public ResponseEntity<LocationDTO> getLocation(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.findLocationById(id));
    }

    @GetMapping("/locations")
    public ResponseEntity<List<LocationDTO>> getAllLocations() {
        return ResponseEntity.ok(locationService.findAllLocations());
    }

    @PostMapping("/locations")
    public ResponseEntity<LocationDTO> createLocation(@RequestBody LocationDTO locationDTO) {
        return ResponseEntity.ok(locationService.saveLocation(locationDTO));
    }

    @PutMapping("/locations/{id}")
    public ResponseEntity<LocationDTO> updateLocation(@PathVariable Long id, @RequestBody LocationDTO locationDTO) {
        return ResponseEntity.ok(locationService.updateLocation(id, locationDTO));
    }

    @DeleteMapping("/locations/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }
}
