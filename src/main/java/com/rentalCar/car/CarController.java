package com.rentalCar.car;

import com.rentalCar.images.CloudinaryService;
import com.rentalCar.images.S3Service;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController()
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    private final S3Service s3Service;

    private final CloudinaryService cloudinaryService;

    @Value("${application.bucket.name}")
    private String bucketName ;

    private static final String UPLOAD_DIR = "src/main/resources/static/images";


    @Autowired
    public CarController(CarService carService, S3Service s3Service, CloudinaryService cloudinaryService) {
        this.carService = carService;
        this.s3Service = s3Service;
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

           // String imageUrl = this.cloudinaryService.uploadFile(image);


        try {
            String imageUrl = s3Service.uploadFile(image, bucketName);

            List<String> details = new ArrayList<String>();

            for (MultipartFile detailImage : detailsImages){
                String detailImageUrl = s3Service.uploadFile(detailImage, bucketName);
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
        }catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
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

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<String> editCar(
            @PathVariable String id,
            @RequestParam(value = "image", required = false) MultipartFile image, // Optional image
            @RequestParam(value = "detailsImages", required = false) List<MultipartFile> detailsImages, // Optional list of images
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
            @RequestParam("numberOfDoors") Long numberOfDoors) throws IOException {

        Car existingCar = carService.getCarById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));


        this.s3Service.deleteOldImages(existingCar,bucketName);



        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            imageUrl = s3Service.uploadFile(image, bucketName);
        }

        List<String> details = existingCar.getDetailsImages() != null ? new ArrayList<>(existingCar.getDetailsImages()) : new ArrayList<>();
        for (MultipartFile detailImage : detailsImages) {
            String detailImageUrl = s3Service.uploadFile(detailImage, bucketName);
            details.add(detailImageUrl);
        }

        existingCar.setMatriculate(matriculate);
        existingCar.setMark(mark);
        existingCar.setModel(model);
        existingCar.setPassengers(passengers);
        existingCar.setType(type);
        existingCar.setNumberOfSeats(numberOfSeats);
        existingCar.setPrice(price);
        existingCar.setReview(review);
        existingCar.setAutomaticTransmission(automaticTransmission);
        existingCar.setAirConditioning(airConditioning);
        existingCar.setNumberOfDoors(numberOfDoors);
        existingCar.setImageUrl(imageUrl != null ? imageUrl : existingCar.getImageUrl()); // Update or keep existing image URL
        existingCar.setDetailsImages(details);

        carService.editCar(existingCar);

        return ResponseEntity.ok().body("Car edited successfully.");
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
        return LocalDate.parse(dateString, formatter);
    }

}
