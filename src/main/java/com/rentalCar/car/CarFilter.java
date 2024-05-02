package com.rentalCar.car;

import java.time.LocalDate;
import java.util.List;

public class CarFilter {

    private List<String> mark;
    private List<String> type;
    private Integer numberOfSeats;
    private LocalDate fromDate;

    private LocalDate toDate;
    private Long priceFrom;
    private Long priceTo;

    private Double review;

    private Boolean automaticTransmission;

    private Boolean airConditioning;

    private Long numberOfDoors;

    public CarFilter() {
    }

    // Constructor, getters, and setters


    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public Double getReview() {
        return review;
    }

    public void setReview(Double review) {
        this.review = review;
    }

    public Boolean isAutomaticTransmission() {
        return automaticTransmission;
    }

    public void setAutomaticTransmission(Boolean automaticTransmission) {
        this.automaticTransmission = automaticTransmission;
    }

    public Boolean isAirConditioning() {
        return airConditioning;
    }

    public void setAirConditioning(Boolean airConditioning) {
        this.airConditioning = airConditioning;
    }

    public Long getNumberOfDoors() {
        return numberOfDoors;
    }

    public void setNumberOfDoors(Long numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }

    public List<String> getMark() {
        return mark;
    }

    public void setMark(List<String> mark) {
        this.mark = mark;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
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

