package com.rentalCar.user;

import jakarta.persistence.*;

@Embeddable
public class SecondDriver {

    private String name;
    private String licenseNumber;

    public SecondDriver( String name, String licenseNumber) {
        this.name = name;
        this.licenseNumber = licenseNumber;
    }

    public SecondDriver() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
}
