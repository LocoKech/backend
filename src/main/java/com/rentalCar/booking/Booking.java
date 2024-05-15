package com.rentalCar.booking;

import com.rentalCar.car.Car;
import com.rentalCar.client.Client;
import com.rentalCar.extras.Extras;
import com.rentalCar.user.SecondDriver;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "matriculate")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @Embedded()
    private SecondDriver secondDriver;

    private LocalDate startDate;

    private LocalDate endDate;
    @ElementCollection
    @CollectionTable(name = "booking_extras", joinColumns = @JoinColumn(name = "booking_id"))
    @MapKeyJoinColumn(name = "extras_id")
    @Column(name = "quantity")
    private Map<Extras, Long> extrasQuantity = new HashMap<>();

    private Double totalPrice;

    public Booking() {
    }

    public Booking(Long id, Car ca, Client client, SecondDriver secondDriver, LocalDate startDate, LocalDate endDate, Double totalPrice) {
        this.id = id;
        this.client = client;
        this.car = car;
        this.secondDriver = secondDriver;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Map<Extras, Long> getExtrasQuantity() {
        return extrasQuantity;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setExtrasQuantity(Map<Extras, Long> extrasQuantity) {
        this.extrasQuantity = extrasQuantity;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }


    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public SecondDriver getSecondDriver() {
        return secondDriver;
    }

    public void setSecondDriver(SecondDriver secondDriver) {
        this.secondDriver = secondDriver;
    }
}
