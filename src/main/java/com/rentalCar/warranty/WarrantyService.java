package com.rentalCar.warranty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarrantyService {

    private final WarrantyRepository warrantyRepository;

    @Autowired
    public WarrantyService(WarrantyRepository warrantyRepository) {
        this.warrantyRepository = warrantyRepository;
    }

    public List<Warranty> getAllWarranties() {
        return warrantyRepository.findAll();
    }

    public Optional<Warranty> getWarrantyById(Long id) {
        return warrantyRepository.findById(id);
    }

    public Warranty createWarranty(Warranty warranty) {
        return warrantyRepository.save(warranty);
    }

    public Warranty updateWarranty(Long id, Warranty warranty) {
        return warrantyRepository.save(warranty);
    }

    public void deleteWarranty(Long id) {
        warrantyRepository.deleteById(id);
    }
}
