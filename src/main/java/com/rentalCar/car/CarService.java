package com.rentalCar.car;


import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car createCar(Car car){
        if (this.carRepository.existsById(car.getMatriculate()))
            throw new EntityExistsException("car already exist");
        return this.carRepository.save(car);
    }

    public Page<Car> getAllCars(CarFilter carFilter, Pageable pageable) {
        Specification<Car> spec = CarSpec.filterBy(carFilter);
        return carRepository.findAll(spec, pageable);
    }

    public List<Car> allCars() {
        return this.carRepository.findAll();
    }

    public List<String> getAllMarks(){
        return this.carRepository.findAllMarks();
    }

    public List<String> getAllTypes(){
        return this.carRepository.findAllMarks();
    }

    public Car editCar(Car car) {
        return  this.carRepository.save(car);
    }

    public void deleteCar(String matriculate) {
        this.carRepository.deleteById(matriculate);
    }

    public Optional<Car> getCarById(String id){
        return this.carRepository.findById(id);
    }

}
