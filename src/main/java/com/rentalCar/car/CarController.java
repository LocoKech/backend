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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

@RestController()
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    private static final String UPLOAD_DIR = "/src/main/resources/images";


    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/cars")
    public ResponseEntity<String> uploadCar(
            @RequestParam("image") MultipartFile image,
            @RequestParam("matriculate") String matriculate,
            @RequestParam("mark") String mark,
            @RequestParam("type") String type,
            @RequestParam("numberOfSeats") int numberOfSeats,
            @RequestParam("price") Double price,
            @RequestParam("review") Double review,
            @RequestParam("automaticTransmission") boolean automaticTransmission,
            @RequestParam("airConditioning") boolean airConditioning,
            @RequestParam("numberOfDoors") Long numberOfDoors) {

        try {

            String imageName = StringUtils.cleanPath(image.getOriginalFilename());
            Path imagePath = Paths.get(UPLOAD_DIR, imageName);
            Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

            Car car = new Car();
            car.setImageUrl(imagePath.toString());
            car.setMatriculate(matriculate);
            car.setMark(mark);
            car.setType(type);
            car.setNumberOfSeats(numberOfSeats);
            car.setPrice(price);
            car.setReview(review);
            car.setAutomaticTransmission(automaticTransmission);
            car.setAirConditioning(airConditioning);
            car.setNumberOfDoors(numberOfDoors);

            carService.createCar(car);

            return ResponseEntity.ok().body("Car uploaded successfully. Image path: " + imagePath);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload car: " + e.getMessage());
        }
    }


    @GetMapping()
    public Page<Car> getCars(
            @RequestParam(required = false) String mark,
            @RequestParam(required = false) String type,
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
}
