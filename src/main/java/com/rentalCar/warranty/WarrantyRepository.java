package com.rentalCar.warranty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarrantyRepository extends JpaRepository<Warranty,Long> {
}
