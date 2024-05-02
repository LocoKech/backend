package com.rentalCar.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Console;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    private static final String UPLOAD_DIR = "src/main/resources/static/images";


    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping()
    public ResponseEntity<String> uploadCar(
            @RequestParam("image") MultipartFile image,
            @RequestParam("matriculate") String matriculate,
            @RequestParam("mark") String mark,
            @RequestParam("model") String model,
            @RequestParam("passengers") Long passengers,
            @RequestParam("type") String type,
            @RequestParam("numberOfSeats") int numberOfSeats,
            @RequestParam("price") Double price,
            @RequestParam("review") Double review,
            @RequestParam("automaticTransmission") Boolean automaticTransmission,
            @RequestParam("airConditioning") Boolean airConditioning,
            @RequestParam("numberOfDoors") Long numberOfDoors) {

        try {

            String uniqueFileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            Path imagePath = Paths.get(UPLOAD_DIR, uniqueFileName);

            System.out.println(image.getOriginalFilename());
            System.out.println(image);


                Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
                String imageUrl =  uniqueFileName;
            Car car = new Car();
            car.setImageUrl(imageUrl);
            car.setMatriculate(matriculate);
            car.setMark(mark);
            car.setType(type);
            car.setNumberOfSeats(numberOfSeats);
            car.setPrice(price);
            car.setReview(review);
            car.setAutomaticTransmission(automaticTransmission);
            car.setAirConditioning(airConditioning);
            car.setNumberOfDoors(numberOfDoors);
            car.setPassengers(passengers);
            car.setModel(model);

            carService.createCar(car);

            System.out.println(car);

            return ResponseEntity.ok().body("Car uploaded successfully. Image path: " + imagePath + car);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload car: " + e.getMessage());
        }
    }


    @GetMapping()
    public Page<Car> getCars(
            @RequestParam(required = false) List<String> mark,
            @RequestParam(required = false) List<String> type,
            @RequestParam(required = false) Integer numberOfSeats,
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate,
            @RequestParam(required = false) Long priceFrom,
            @RequestParam(required = false) Long priceTo,
            @RequestParam(required = false) Double reviewFrom,
            @RequestParam(required = false) Boolean automaticTransmission,
            @RequestParam(required = false) Boolean airConditioning,
            @RequestParam(required = false) Long numberOfDoors,
            @RequestParam(defaultValue = "matriculate") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        CarFilter carFilter = new CarFilter();
        carFilter.setMark(mark);
        carFilter.setType(type);
        carFilter.setNumberOfSeats(numberOfSeats);
        carFilter.setToDate(toDate != null ? LocalDate.parse(toDate) : null);
        carFilter.setFromDate(fromDate != null ? LocalDate.parse(fromDate) : null);
        carFilter.setPriceFrom(priceFrom);
        carFilter.setPriceTo(priceTo);
        carFilter.setReview(reviewFrom);
        carFilter.setAutomaticTransmission(automaticTransmission);
        carFilter.setAirConditioning(airConditioning);
        carFilter.setNumberOfDoors(numberOfDoors);

        PageRequest pageable = PageRequest.of(page, size , Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        return carService.getAllCars(carFilter, pageable);
    }

    @GetMapping("all")
    public List<Car> getAllCars(){
        return this.carService.allCars();
    }
}
