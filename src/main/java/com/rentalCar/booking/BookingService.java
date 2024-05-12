package com.rentalCar.booking;

import com.rentalCar.car.Car;
import com.rentalCar.car.CarRepository;
import com.rentalCar.extras.Extras;
import com.rentalCar.extras.ExtrasRepository;
import com.rentalCar.user.User;
import com.rentalCar.user.UserRepository;
import com.sun.source.tree.LambdaExpressionTree;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    private final CarRepository carRepository;

    private final com.rentalCar.user.UserRepository userRepository;

    private final ExtrasRepository extrasRepository;


    @Autowired()
    public BookingService(BookingRepository bookingRepository, CarRepository carRepository, UserRepository userRepository, CarRepository carRepository1, UserRepository userRepository1, ExtrasRepository extrasRepository) {
        this.bookingRepository = bookingRepository;
        this.carRepository = carRepository1;
        this.userRepository = userRepository1;
        this.extrasRepository = extrasRepository;
    }


    public List<Booking> getBookings() {
        return this.bookingRepository.findAll();
    }

    public Optional<Booking> getBooking(Long id) {
        return this.bookingRepository.findById(id);
    }

    public Booking createBooking(BookingRequest bookingRequest) {

            Car car = this.carRepository.findById(bookingRequest.getCarId())
                    .orElseThrow(() -> new EntityNotFoundException("Car not found"));

            User user = this.userRepository.findById(bookingRequest.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));

            Booking booking = new Booking();

        Double price = car.getPrice();

        booking.setCar(car);
        booking.setUser(user);
        booking.setStartDate(bookingRequest.getStartDate());
        booking.setEndDate(bookingRequest.getEndDate());

        Map<Extras, Long> extrasQuantity = new HashMap<>();
        for (BookingExtraRequest extraRequest : bookingRequest.getExtraRequests()) {
            Extras extra = this.extrasRepository.findById(extraRequest.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Extra not found"));
            extrasQuantity.put(extra, extraRequest.getQuantity());

            price += extra.getPrice() * extraRequest.getQuantity();
        }
        booking.setExtrasQuantity(extrasQuantity);
        booking.setTotalPrice(price);

        return bookingRepository.save(booking);
    }


    public Booking createBookingAndUser(BookingAndUserRequest bookingAndUserRequest) {

        User user = this.userRepository.save(bookingAndUserRequest.getUser());

        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setExtraRequests(bookingAndUserRequest.getExtraRequests());
        bookingRequest.setUserId(user.getUuid());
        bookingRequest.setCarId(bookingAndUserRequest.getMatriculate());
        bookingRequest.setEndDate(bookingAndUserRequest.getEndDate());
        bookingRequest.setStartDate(bookingAndUserRequest.getStartDate());

        return this.createBooking(bookingRequest);
    }

}
