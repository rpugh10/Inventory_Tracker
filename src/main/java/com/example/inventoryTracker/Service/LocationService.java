package com.example.inventoryTracker.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.inventoryTracker.DTO.RequestDTOS.LocationRequestDTO;
import com.example.inventoryTracker.DTO.ResponseDTOS.LocationResponseDTO;
import com.example.inventoryTracker.Entities.Location;
import com.example.inventoryTracker.Mapper.LocationMapper;
import com.example.inventoryTracker.Repository.LocationRepository;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    public LocationService(LocationRepository locationRepository, LocationMapper locationMapper) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
    }

    public LocationResponseDTO findLocationById(Long id) {
        return locationRepository.findById(id)
                .map(locationMapper::toLocationDTO)
                .orElseThrow(() -> new RuntimeException("Location not found with id: " + id));
    }

    public List<LocationResponseDTO> findAllLocations() {
        return locationRepository.findAll().stream().map(locationMapper::toLocationDTO).toList();
    }

    public LocationResponseDTO saveLocation(LocationRequestDTO locationDTO) {
        Location savedLocation = locationRepository.save(locationMapper.toLocation(locationDTO));
        return locationMapper.toLocationDTO(savedLocation);
    }

    public LocationResponseDTO updateLocation(Long id, LocationRequestDTO locationDTO) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found with id: " + id));
        location.setLocationName(locationDTO.getLocationName());
        return locationMapper.toLocationDTO(locationRepository.save(location));
    }

    public void deleteLocation(Long id) {
        if (!locationRepository.existsById(id)) {
            throw new RuntimeException("Location not found with id: " + id);
        }
        locationRepository.deleteById(id);
    }
}
