package com.rentalCar.auth;

import com.rentalCar.user.UserRole;

public record SignUpDto(
        String login,
        String password,

        String email,
        UserRole role) {
}