package com.rentalCar.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController()
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping()
    public Car createCar(@RequestBody Car car) {
        return carService.createCar(car);
    }

    @GetMapping()
    public Page<Car> getCars(
            @RequestParam(required = false) String mark,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Integer numberOfSeats,
            @RequestParam(required = false) String reservationDate,
            @RequestParam(required = false) Long priceFrom,
            @RequestParam(required = false) Long priceTo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        CarFilter carFilter = new CarFilter();
        carFilter.setMark(mark);
        carFilter.setType(type);
        carFilter.setNumberOfSeats(numberOfSeats);
        carFilter.setReservationDate(reservationDate != null ? LocalDate.parse(reservationDate) : null);
        carFilter.setPriceFrom(priceFrom);
        carFilter.setPriceTo(priceTo);

        PageRequest pageable = PageRequest.of(page, size);
        return carService.getAllCars(carFilter, pageable);
    }
}
