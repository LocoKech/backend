package com.rentalCar.car;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    private String matriculate;

    private String mark;

    private String type;

    private Integer numberOfSeats;

    private Double price;

    private Double review;

    private String imageUrl;

    private boolean automaticTransmission;

    private boolean airConditioning;

    private Long numberOfDoors;

    public Car(String matriculate, String mark, String type, Integer numberOfSeats, Double price, Double review, String imageUrl, boolean automaticTransmission, boolean airConditioning, Long numberOfDoors) {
        this.matriculate = matriculate;
        this.mark = mark;
        this.type = type;
        this.numberOfSeats = numberOfSeats;
        this.price = price;
        this.review = review;
        this.imageUrl = imageUrl;
        this.automaticTransmission = automaticTransmission;
        this.airConditioning = airConditioning;
        this.numberOfDoors = numberOfDoors;
    }

    public Car() {
    }

    public Double getReview() {
        return review;
    }

    public void setReview(Double review) {
        this.review = review;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
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

}
