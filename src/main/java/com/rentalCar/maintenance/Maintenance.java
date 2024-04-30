package com.rentalCar.maintenance;

import com.rentalCar.car.Car;
import jakarta.persistence.*;

@Entity
@Table(name = "maintenances")
public class Maintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String taskName;
    private String description;
    private String frequency;


    @ManyToOne()
    @JoinColumn(name = "vehicle_id")
    private Car car;

    public Maintenance() {
    }

    public Maintenance(Long id, String taskName, String description, String frequency, Car car) {
        this.id = id;
        this.taskName = taskName;
        this.description = description;
        this.frequency = frequency;
        this.car = car;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
