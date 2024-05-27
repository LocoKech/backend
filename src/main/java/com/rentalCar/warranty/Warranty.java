package com.rentalCar.warranty;

import com.rentalCar.car.Car;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

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

    private Double cost;

    private List<String> invoices;

    public Warranty() {
    }

    public Warranty(Long id, Car car, String warrantyProvider, LocalDate startDate, Long coveragePeriod, LocalDate endDate, Double cost, List<String> invoices) {
        this.id = id;
        this.car = car;
        this.warrantyProvider = warrantyProvider;
        this.startDate = startDate;
        this.coveragePeriod = coveragePeriod;
        this.endDate = endDate;
        this.cost = cost;
        this.invoices = invoices;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
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

    public List<String> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<String> invoices) {
        this.invoices = invoices;
    }
}
