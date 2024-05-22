package com.rentalCar.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    public Optional<User> getUserById(UUID id) {
        return this.userRepository.findById(id);
    }

    public User createUser(User newUser) {
        return this.userRepository.save(newUser);
    }


    public User editUser(User newUser) {
        return this.userRepository.save(newUser);
    }

    public void deleteUser(UUID id){
        this.userRepository.deleteById(id);
    }

    public User getUserByUsername(String username) {
        return this.userRepository.findByUserName(username);
    }
}
