package com.rentalCar.auth;

import com.rentalCar.user.User;
import com.rentalCar.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        var user = repository.findByUsername(username);
        return user;
    }

    public UserDetails signUp(SignUpDto data) throws IllegalArgumentException {
        if (repository.findByUsername(data.login()) != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User();
        newUser.setUsername(data.login());
        newUser.setEmail(data.email());
        newUser.setPassword(encryptedPassword);
        newUser.setRole(data.role());
        return repository.save(newUser);
    }
}