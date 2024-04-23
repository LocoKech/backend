package com.rentalCar.car;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car createCar(Car car){
        return this.carRepository.save(car);
    }

    public Page<Car> getAllCars(CarFilter carFilter, Pageable pageable) {
        Specification<Car> spec = CarSpec.filterBy(carFilter);
        return carRepository.findAll(spec, pageable);
    }
}
