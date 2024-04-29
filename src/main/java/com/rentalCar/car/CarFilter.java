package com.rentalCar.car;

import java.time.LocalDate;

public class CarFilter {

    private String mark;
    private String type;
    private Integer numberOfSeats;
    private LocalDate reservationDate;
    private Long priceFrom;
    private Long priceTo;

    private Double review;

    private boolean automaticTransmission;

    private boolean airConditioning;

    private Long numberOfDoors;

    public CarFilter() {
    }

    // Constructor, getters, and setters


    public Double getReview() {
        return review;
    }

    public void setReview(Double review) {
        this.review = review;
    }

    public boolean isAutomaticTransmission() {
        return automaticTransmission;
    }

    public void setAutomaticTransmission(boolean automaticTransmission) {
        this.automaticTransmission = automaticTransmission;
    }

    public boolean isAirConditioning() {
        return airConditioning;
    }

    public void setAirConditioning(boolean airConditioning) {
        this.airConditioning = airConditioning;
    }

    public Long getNumberOfDoors() {
        return numberOfDoors;
    }

    public void setNumberOfDoors(Long numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
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

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Long getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(Long priceFrom) {
        this.priceFrom = priceFrom;
    }

    public Long getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(Long priceTo) {
        this.priceTo = priceTo;
    }
}

