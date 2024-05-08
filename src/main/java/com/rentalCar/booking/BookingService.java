package com.rentalCar.booking;

import com.rentalCar.car.Car;
import com.rentalCar.car.CarRepository;
import com.rentalCar.user.User;
import com.rentalCar.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    private final CarRepository carRepository;

    private final com.rentalCar.user.UserRepository userRepository;


    @Autowired()
    public BookingService(BookingRepository bookingRepository, com.rentalCar.car.CarRepository carRepository, com.rentalCar.user.UserRepository userRepository, CarRepository carRepository1, UserRepository userRepository1) {
        this.bookingRepository = bookingRepository;
        this.carRepository = carRepository1;
        this.userRepository = userRepository1;
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

        booking.setCar(car);
        booking.setUser(user);
        booking.setStartDate(bookingRequest.getStartDate());
        booking.setEndDate(bookingRequest.getEndDate());

        return bookingRepository.save(booking);
    }


    public Booking createBookingAndUser(BookingAndUserRequest bookingAndUserRequest) {

        Car car = this.carRepository.findById(bookingAndUserRequest.getMatriculate())
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));

        Booking booking = new Booking();
        booking.setUser(bookingAndUserRequest.getUser());
        booking.setEndDate(bookingAndUserRequest.getEndDate());
        booking.setStartDate(bookingAndUserRequest.getStartDate());
        booking.setCar(car);
        return booking;
    }
}
