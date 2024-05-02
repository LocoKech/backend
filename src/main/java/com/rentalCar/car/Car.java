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

    private String model;

    private Long passengers;

    private String type;

    private Integer numberOfSeats;

    private Double price;

    private Double review;

    private String imageUrl;

    private Boolean automaticTransmission;

    private Boolean airConditioning;

    private Long numberOfDoors;

    public Car(String matriculate, String mark, String model, Long passengers, String type, Integer numberOfSeats, Double price, Double review, String imageUrl, boolean automaticTransmission, boolean airConditioning, Long numberOfDoors) {
        this.matriculate = matriculate;
        this.mark = mark;
        this.model = model;
        this.passengers = passengers;
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getPassengers() {
        return passengers;
    }

    public void setPassengers(Long passengers) {
        this.passengers = passengers;
    }

    public Boolean getAutomaticTransmission() {
        return automaticTransmission;
    }

    public Boolean getAirConditioning() {
        return airConditioning;
    }

    @Override
    public String toString() {
        return "Car{" +
                "matriculate='" + matriculate + '\'' +
                ", mark='" + mark + '\'' +
                ", type='" + type + '\'' +
                ", numberOfSeats=" + numberOfSeats +
                ", price=" + price +
                ", review=" + review +
                ", imageUrl='" + imageUrl + '\'' +
                ", automaticTransmission=" + automaticTransmission +
                ", airConditioning=" + airConditioning +
                ", numberOfDoors=" + numberOfDoors +
                '}';
    }
}
