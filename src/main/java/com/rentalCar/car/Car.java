package com.rentalCar.car;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    private String matriculate;

    private String mark;

    private String type;

    private Integer numberOfSeats;

    private LocalDate reservationEndDate;

    private Long price;

    public Car(String matriculate, String mark, String type, Integer numberOfSeats, LocalDate reservationEndDate, Long price) {
        this.matriculate = matriculate;
        this.mark = mark;
        this.type = type;
        this.numberOfSeats = numberOfSeats;
        this.reservationEndDate = reservationEndDate;
        this.price = price;
    }

    public Car() {
    }

    public Long getPrice() {
        return this.price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }


    public String getMatriculate() {
        return matriculate;
    }

    public void setMatriculate(String matriculate) {
        this.matriculate = matriculate;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public LocalDate getReservationEndDate() {
        return reservationEndDate;
    }

    public void setReservationEndDate(LocalDate reservationEndDate) {
        this.reservationEndDate = reservationEndDate;
    }
}
