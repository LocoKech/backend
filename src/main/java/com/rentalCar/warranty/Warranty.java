package com.rentalCar.warranty;

import com.rentalCar.car.Car;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "warranties")
public class Warranty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Car car;

    private String warrantyProvider;

    private LocalDate startDate;

    private Long coveragePeriod;

    private LocalDate endDate;

    public Warranty() {
    }

    public Warranty(Long id, Car car, String warrantyProvider, LocalDate startDate, Long coveragePeriod, LocalDate endDate) {
        this.id = id;
        this.car = car;
        this.warrantyProvider = warrantyProvider;
        this.startDate = startDate;
        this.coveragePeriod = coveragePeriod;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getWarrantyProvider() {
        return warrantyProvider;
    }

    public void setWarrantyProvider(String warrantyProvider) {
        this.warrantyProvider = warrantyProvider;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Long getCoveragePeriod() {
        return coveragePeriod;
    }

    public void setCoveragePeriod(Long coveragePeriod) {
        this.coveragePeriod = coveragePeriod;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
