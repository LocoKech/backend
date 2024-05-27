package com.rentalCar.maintenance;

import com.rentalCar.car.Car;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "maintenances")
public class Maintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String taskName;
    private String description;
    @Enumerated(EnumType.STRING)
    private Frequency frequency;

    private LocalDate lastMaintenanceDate;

    private Double cost;


    @ManyToOne()
    @JoinColumn(name = "vehicle_id")
    private Car car;

    private List<String> invoices;

    public Maintenance() {
    }

    public Maintenance(Long id, String taskName, String description, Frequency frequency, LocalDate lastMaintenanceDate, Double cost, Car car, List<String> invoices) {
        this.id = id;
        this.taskName = taskName;
        this.description = description;
        this.frequency = frequency;
        this.lastMaintenanceDate = lastMaintenanceDate;
        this.cost = cost;
        this.car = car;
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

    public List<String> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<String> invoices) {
        this.invoices = invoices;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public LocalDate getLastMaintenanceDate() {
        return lastMaintenanceDate;
    }

    public void setLastMaintenanceDate(LocalDate lastMaintenanceDate) {
        this.lastMaintenanceDate = lastMaintenanceDate;
    }
}
