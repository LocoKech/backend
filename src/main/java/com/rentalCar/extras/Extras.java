package com.rentalCar.extras;

import jakarta.persistence.*;

@Entity
@Table(name = "extras")
public class Extras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double price;

    private Long max;



    public Extras(Long id, String name, Double price, Long max) {
        this.id = id;
        this.name = name;
        this.price = price;

        this.max = max;
    }

    public Extras() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }
}
