package com.rentalCar.booking;

import java.time.LocalDate;
import java.util.UUID;

public class BookingRequest {
    private String carId;
    private UUID userId;
    private LocalDate startDate;
    private LocalDate endDate;

    public BookingRequest() {
    }

    public BookingRequest(String carId, UUID userId, LocalDate startDate, LocalDate endDate) {
        this.carId = carId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
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
