package com.rentalCar.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car,String>, JpaSpecificationExecutor<Car> {

    @Query("SELECT DISTINCT c.mark FROM Car c")
    List<String> findAllMarks();

    @Query("SELECT DISTINCT c.type FROM Car c")
    List<String> findAllTypes();
}
