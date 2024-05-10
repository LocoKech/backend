package com.rentalCar.booking;

import com.rentalCar.car.Car;
import com.rentalCar.extras.Extras;
import com.rentalCar.user.User;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    @JoinColumn(name = "user_id", referencedColumnName = "uuid")
    private User user;

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

    public Booking(Long id, Car car, User user, LocalDate startDate, LocalDate endDate, Double totalPrice) {
        this.id = id;
        this.car = car;
        this.user = user;
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

    public void setExtrasQuantity(Map<Extras, Long> extrasQuantity) {
        this.extrasQuantity = extrasQuantity;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}
