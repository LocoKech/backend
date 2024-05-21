package com.rentalCar.booking;

import com.rentalCar.car.Car;
import com.rentalCar.car.CarRepository;
import com.rentalCar.client.Client;
import com.rentalCar.client.ClientRepository;
import com.rentalCar.extras.Extras;
import com.rentalCar.extras.ExtrasRepository;
import com.rentalCar.user.SecondDriver;
import com.rentalCar.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    private final CarRepository carRepository;

    private final ClientRepository clientRepository;

    private final ExtrasRepository extrasRepository;

    private final EmailService emailService;

    @Autowired
    private SpringTemplateEngine templateEngine;



    @Autowired()
    public BookingService(BookingRepository bookingRepository, CarRepository carRepository, UserRepository userRepository, CarRepository carRepository1, UserRepository userRepository1, ClientRepository clientRepository, ExtrasRepository extrasRepository, EmailService emailService) {
        this.bookingRepository = bookingRepository;
        this.carRepository = carRepository1;
        this.clientRepository = clientRepository;
        this.extrasRepository = extrasRepository;
        this.emailService = emailService;
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
      //  if (carRepository.isCarAvailable(car.getMatriculate(), bookingRequest.getStartDate(), bookingRequest.getEndDate())) {
        //    throw new IllegalArgumentException("Car is not available for the selected dates");
        //}

        // Check if start date is before end date
        if (bookingRequest.getStartDate().isAfter(bookingRequest.getEndDate())) {
            throw new IllegalArgumentException("Start date must be before end date");
        }

        Booking booking = new Booking();
        long durationInDays = ChronoUnit.DAYS.between(bookingRequest.getStartDate(), bookingRequest.getEndDate());

        Double price = car.getPrice() * durationInDays;

        booking.setCar(car);
        booking.setClient(client);
        booking.setStartDate(bookingRequest.getStartDate());
        booking.setEndDate(bookingRequest.getEndDate());

        Map<Extras, Long> extrasQuantity = new HashMap<>();
        if (bookingRequest.getExtraRequests() != null)
        for (BookingExtraRequest extraRequest : bookingRequest.getExtraRequests()) {
            Extras extra = extrasRepository.findById(extraRequest.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Extra not found"));
            extrasQuantity.put(extra, extraRequest.getQuantity());
            price += extra.getPrice() * extraRequest.getQuantity();
        }

        booking.setExtrasQuantity(extrasQuantity);
        booking.setTotalPrice(price);

        System.out.println(bookingRequest.getSecondDriver());
        if (bookingRequest.getSecondDriver() != null) {
            SecondDriver secondDriver = new SecondDriver();
            secondDriver.setLicenseNumber(bookingRequest.getSecondDriver().getLicenseNumber());
            secondDriver.setName(bookingRequest.getSecondDriver().getName());
            booking.setSecondDriver(secondDriver);
        }

        Booking savedBooking  = bookingRepository.save(booking);
        this.emailService.sendBookingConfirmationEmail(booking,booking.getClient().getEmail());
        return savedBooking;

    }



    public Booking createBookingAndUser(BookingAndUserRequest bookingAndUserRequest) {

        Client client = this.clientRepository.save(bookingAndUserRequest.getClient());

        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setExtraRequests(bookingAndUserRequest.getExtraRequests());
        bookingRequest.setClientId(client.getId());
        bookingRequest.setCarId(bookingAndUserRequest.getMatriculate());
        bookingRequest.setEndDate(bookingAndUserRequest.getEndDate());
        bookingRequest.setStartDate(bookingAndUserRequest.getStartDate());
        bookingRequest.setSecondDriver(bookingAndUserRequest.getSecondDriver());

        return this.createBooking(bookingRequest);
    }


    public String getContract(Long id) {
        Booking booking = this.bookingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("booking not found"));
        Context context = new Context();
        context.setVariable("booking", booking);

        String htmlContent = templateEngine.process("contract", context);
        return htmlContent;
    }
    public ResponseEntity<ByteArrayResource> getContractAsPdf(Long id) {
        Booking booking = this.bookingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        Context context = new Context();
        context.setVariable("booking", booking);

        String htmlContent = templateEngine.process("contract", context);

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream);
            outputStream.close();

            byte[] pdfBytes = outputStream.toByteArray();
            ByteArrayResource resource = new ByteArrayResource(pdfBytes);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=contract.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(pdfBytes.length)
                    .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
