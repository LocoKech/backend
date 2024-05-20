package com.rentalCar.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car,String>, JpaSpecificationExecutor<Car> {

    @Query("SELECT DISTINCT c.mark FROM Car c")
    List<String> findAllMarks();

    @Query("SELECT DISTINCT c.type FROM Car c")
    List<String> findAllTypes();

    @Query("SELECT COUNT(b) = 0 FROM Booking b WHERE b.car.matriculate = :carId AND " +
            "(:startDate BETWEEN b.startDate AND b.endDate OR :endDate BETWEEN b.startDate AND b.endDate OR " +
            "b.startDate BETWEEN :startDate AND :endDate OR b.endDate BETWEEN :startDate AND :endDate)")
    boolean isCarAvailable(@Param("carId") String carId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
