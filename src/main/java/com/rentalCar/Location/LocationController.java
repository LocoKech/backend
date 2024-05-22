package com.rentalCar.Location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;

    @PostMapping
    public ResponseEntity<Location> saveLocation(@RequestBody LocationRequest locationRequest) {
        Location location = new Location();
        location.setLatitude(locationRequest.getLatitude());
        location.setLongitude(location.getLongitude());
        location.setCarId(locationRequest.getCarId());
        Location savedLocation = locationRepository.save(location);
        return ResponseEntity.ok(savedLocation);
    }

    @GetMapping("/{carId}")
    public ResponseEntity<List<Location>> getLocationsByCarId(@PathVariable String carId) {
        List<Location> locations = locationRepository.findByCarId(carId);
        return ResponseEntity.ok(locations);
    }
}
