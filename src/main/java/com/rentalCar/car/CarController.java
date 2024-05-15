package com.rentalCar.car;

import com.rentalCar.images.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController()
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    private final CloudinaryService cloudinaryService;

    private static final String UPLOAD_DIR = "src/main/resources/static/images";


    @Autowired
    public CarController(CarService carService, CloudinaryService cloudinaryService) {
        this.carService = carService;
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping()
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<String> uploadCar(
            @RequestParam("image") MultipartFile image,
            @RequestParam("detailsImages") List<MultipartFile> detailsImages,
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

            String imageUrl = this.cloudinaryService.uploadFile(image);

            List<String> details = new ArrayList<String>();

            for (MultipartFile detailImage : detailsImages){
                String detailImageUrl = this.cloudinaryService.uploadFile(detailImage);
                details.add(detailImageUrl);
            }

            Car car = new Car();
            car.setImageUrl(imageUrl);
            car.setDetailsImages(details);
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

            return ResponseEntity.ok().body("Car uploaded successfully.");

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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");



        CarFilter carFilter = new CarFilter();
        carFilter.setMark(mark);
        carFilter.setType(type);
        carFilter.setNumberOfSeats(numberOfSeats);
        carFilter.setToDate(toDate != null ? LocalDate.parse(toDate,formatter) : null);
        carFilter.setFromDate(fromDate != null ?  LocalDate.parse(fromDate,formatter) : null);
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


    @GetMapping("/types")
    public ResponseEntity<List<String>> getAllTypes(){
        List<String> types = this.carService.getAllTypes();
        return ResponseEntity.ok(types);
    }

    @GetMapping("/marks")
    public ResponseEntity<List<String>> getAllMarks(){
        List<String> marks = this.carService.getAllMarks();
        return ResponseEntity.ok(marks);
    }

    @PutMapping("")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Car> editCar(@RequestBody Car car){
        return ResponseEntity.ok(this.carService.editCar(car));
    }

    @DeleteMapping("/{matriculate}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public void deleteCar(@PathVariable String matriculate){
        this.carService.deleteCar(matriculate);
    }



    private LocalDate parseLocalDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd yyyy HH:mm:ss 'GMT' Z '('z')'", Locale.ENGLISH);
        return LocalDate.parse(dateString, formatter);
    }
}
