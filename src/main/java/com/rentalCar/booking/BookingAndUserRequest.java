package com.rentalCar.booking;

import com.rentalCar.client.Client;
import com.rentalCar.user.SecondDriver;
import com.rentalCar.user.User;

import java.time.LocalDate;
import java.util.List;

public class BookingAndUserRequest {

    private Client client;

    private SecondDriver secondDriver;

    private LocalDate startDate;
    private LocalDate endDate;

    private String matriculate;

    private List<BookingExtraRequest> extraRequests;

    public BookingAndUserRequest() {
    }

    public BookingAndUserRequest(Client client, SecondDriver secondDriver, LocalDate startDate, LocalDate endDate, String matriculate, List<BookingExtraRequest> extraRequests) {
        this.client = client;
        this.secondDriver = secondDriver;
        this.startDate = startDate;
        this.endDate = endDate;
        this.matriculate = matriculate;
        this.extraRequests = extraRequests;
    }

    public String getMatriculate() {
        return matriculate;
    }

    public void setMatriculate(String matriculate) {
        this.matriculate = matriculate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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

    public List<BookingExtraRequest> getExtraRequests() {
        return extraRequests;
    }

    public void setExtraRequests(List<BookingExtraRequest> extraRequests) {
        this.extraRequests = extraRequests;
    }

    public SecondDriver getSecondDriver() {
        return secondDriver;
    }

    public void setSecondDriver(SecondDriver secondDriver) {
        this.secondDriver = secondDriver;
    }
}
