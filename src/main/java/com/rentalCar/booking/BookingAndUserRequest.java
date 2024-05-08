package com.rentalCar.booking;

import com.rentalCar.user.User;

import java.time.LocalDate;

public class BookingAndUserRequest {

    private User user;
    private LocalDate startDate;
    private LocalDate endDate;

    private String matriculate;

    public BookingAndUserRequest() {
    }

    public BookingAndUserRequest(User user, LocalDate startDate, LocalDate endDate, String matriculate) {
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.matriculate = matriculate;
    }

    public String getMatriculate() {
        return matriculate;
    }

    public void setMatriculate(String matriculate) {
        this.matriculate = matriculate;
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
