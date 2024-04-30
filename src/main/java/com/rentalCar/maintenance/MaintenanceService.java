package com.rentalCar.maintenance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    @Autowired
    public MaintenanceService(MaintenanceRepository maintenanceRepository) {
        this.maintenanceRepository = maintenanceRepository;
    }


    public Maintenance createMaintenance(Maintenance maintenance) {
        return this.maintenanceRepository.save(maintenance);
    }

    public Maintenance getMaintenanceById(Long id) {
        return this.maintenanceRepository.getReferenceById(id);
    }

    public Maintenance updateMaintenance(Maintenance maintenance) {
        return this.maintenanceRepository.save(maintenance);
    }

    public void deleteMaintenance(Long id) {
        this.maintenanceRepository.deleteById(id);
    }

    public List<Maintenance> getAllMaintenance() {
        return this.maintenanceRepository.findAll();
    }
}
