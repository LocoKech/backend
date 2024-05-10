package com.rentalCar.booking;

import com.rentalCar.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired()
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping()
    public List<Booking> getAllBookings(){
        return this.bookingService.getBookings();
    }

    @GetMapping("/{id}")
    public Optional<Booking> getBooking(@PathVariable("id") Long id){
        return this.bookingService.getBooking(id);
    }

    @PostMapping()
    public Booking createBooking(@RequestBody BookingRequest bookingRequest) {
        return bookingService.createBooking(bookingRequest);
    }

    @PostMapping("/client")
    public Booking createBookingAndUser(@RequestBody() BookingAndUserRequest bookingAndUserRequest) {
        return bookingService.createBookingAndUser(bookingAndUserRequest);
    }

}
