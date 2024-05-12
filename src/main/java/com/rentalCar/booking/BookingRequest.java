package com.rentalCar.booking;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class BookingRequest {
    private String carId;
    private UUID userId;
    private LocalDate startDate;
    private LocalDate endDate;

    private List<BookingExtraRequest> extraRequests;

    public BookingRequest() {
    }

    public BookingRequest(String carId, UUID userId, LocalDate startDate, LocalDate endDate, List<BookingExtraRequest> extraRequests) {
        this.carId = carId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
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
