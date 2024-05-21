package com.rentalCar.booking;

import com.rentalCar.user.User;
import org.eclipse.angus.mail.iap.ByteArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<Booking> getAllBookings(){
        return this.bookingService.getBookings();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
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

    @GetMapping("/{id}/contract")
    public ResponseEntity<ByteArrayResource> getContract(@PathVariable Long id) {

       // String htmlContent = this.bookingService.getContract(id);

        return this.bookingService.getContractAsPdf(id);
    }

}
