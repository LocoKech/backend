package com.rentalCar.booking;

import com.rentalCar.user.SecondDriver;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class BookingRequest {
    private String carId;
    private Long clientId;
    private LocalDate startDate;
    private LocalDate endDate;

    private SecondDriver secondDriver;

    private List<BookingExtraRequest> extraRequests;

    public BookingRequest() {
    }

    public BookingRequest(Long clientId, UUID userId, LocalDate startDate, LocalDate endDate, SecondDriver secondDriver, List<BookingExtraRequest> extraRequests) {
        this.carId = carId;
        this.clientId = clientId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.secondDriver = secondDriver;
        this.extraRequests = extraRequests;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public List<BookingExtraRequest> getExtraRequests() {
        return extraRequests;
    }

    public void setExtraRequests(List<BookingExtraRequest> extraRequests) {
        this.extraRequests = extraRequests;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
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
