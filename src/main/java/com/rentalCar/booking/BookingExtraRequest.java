package com.rentalCar.booking;

public class BookingExtraRequest {

    private Long id;

    private Long quantity;

    public BookingExtraRequest() {
    }

    public BookingExtraRequest(Long id, Long quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
