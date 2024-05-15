package com.rentalCar.auth;

public record SignInDto(
        String login,
        String password) {
}