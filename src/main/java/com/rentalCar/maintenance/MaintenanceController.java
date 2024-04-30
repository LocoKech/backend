package com.rentalCar.maintenance;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("maintenance")
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @PostMapping
    public ResponseEntity<Maintenance> createMaintenance(@RequestBody Maintenance maintenance) {
        Maintenance createdMaintenance = maintenanceService.createMaintenance(maintenance);
        return new ResponseEntity<>(createdMaintenance, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Maintenance> getMaintenanceById(@PathVariable Long id) {
        Maintenance maintenance = maintenanceService.getMaintenanceById(id);
        return new ResponseEntity<>(maintenance, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Maintenance> updateMaintenance(@RequestBody Maintenance maintenance) {
        Maintenance updatedMaintenance = maintenanceService.updateMaintenance(maintenance);
        return new ResponseEntity<>(updatedMaintenance, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaintenance(@PathVariable Long id) {
        maintenanceService.deleteMaintenance(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Maintenance>> getAllMaintenance() {
        List<Maintenance> maintenanceList = maintenanceService.getAllMaintenance();
        return new ResponseEntity<>(maintenanceList, HttpStatus.OK);
    }
}
