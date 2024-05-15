package com.rentalCar.booking;

import com.rentalCar.car.Car;
import com.rentalCar.car.CarRepository;
import com.rentalCar.client.Client;
import com.rentalCar.client.ClientRepository;
import com.rentalCar.extras.Extras;
import com.rentalCar.extras.ExtrasRepository;
import com.rentalCar.user.User;
import com.rentalCar.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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

    private final ClientRepository clientRepository;

    private final ExtrasRepository extrasRepository;


    @Autowired()
    public BookingService(BookingRepository bookingRepository, CarRepository carRepository, UserRepository userRepository, CarRepository carRepository1, UserRepository userRepository1, ClientRepository clientRepository, ExtrasRepository extrasRepository) {
        this.bookingRepository = bookingRepository;
        this.carRepository = carRepository1;
        this.clientRepository = clientRepository;
        this.extrasRepository = extrasRepository;
    }


    public List<Booking> getBookings() {
        return this.bookingRepository.findAll();
    }

    public Optional<Booking> getBooking(Long id) {
        return this.bookingRepository.findById(id);
    }

    @Transactional
    public Booking createBooking(BookingRequest bookingRequest) {

        Car car = carRepository.findById(bookingRequest.getCarId())
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));

        Client client = clientRepository.findById(bookingRequest.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Check if the car is available for the given dates
        // Assuming there is a method in the repository to check for availability
        if (!carRepository.isCarAvailable(car.getMatriculate(), bookingRequest.getStartDate(), bookingRequest.getEndDate())) {
            throw new IllegalArgumentException("Car is not available for the selected dates");
        }

        // Check if start date is before end date
        if (bookingRequest.getStartDate().isAfter(bookingRequest.getEndDate())) {
            throw new IllegalArgumentException("Start date must be before end date");
        }

        Booking booking = new Booking();
        Double price = car.getPrice();

        booking.setCar(car);
        booking.setClient(client);
        booking.setStartDate(bookingRequest.getStartDate());
        booking.setEndDate(bookingRequest.getEndDate());

        Map<Extras, Long> extrasQuantity = new HashMap<>();
        for (BookingExtraRequest extraRequest : bookingRequest.getExtraRequests()) {
            Extras extra = extrasRepository.findById(extraRequest.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Extra not found"));
            extrasQuantity.put(extra, extraRequest.getQuantity());
            price += extra.getPrice() * extraRequest.getQuantity();
        }
        booking.setExtrasQuantity(extrasQuantity);
        booking.setTotalPrice(price);

        // Handle optional second driver
        if (bookingRequest.getSecondDriver() != null) {
            booking.setSecondDriver(bookingRequest.getSecondDriver());
        }

        return bookingRepository.save(booking);
    }



    public Booking createBookingAndUser(BookingAndUserRequest bookingAndUserRequest) {

        Client client = this.clientRepository.save(bookingAndUserRequest.getClient());

        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setExtraRequests(bookingAndUserRequest.getExtraRequests());
        bookingRequest.setClientId(client.getId());
        bookingRequest.setCarId(bookingAndUserRequest.getMatriculate());
        bookingRequest.setEndDate(bookingAndUserRequest.getEndDate());
        bookingRequest.setStartDate(bookingAndUserRequest.getStartDate());

        return this.createBooking(bookingRequest);
    }

}
