package com.rentalCar.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController()
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping()
    public List<User> getUsers(){
        return this.userService.getUsers();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable("id") UUID id) {
        return this.userService.getUserById(id);
    }

    @PostMapping()
    public User createUser(@RequestBody User newUser){
        return this.userService.createUser(newUser);
    }

    @PutMapping()
    public User editUser(@RequestBody User newUser){
        return this.userService.editUser(newUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id){
        this.userService.deleteUser(id);
    }

    @GetMapping("/username/{username}")
    public User getUser(@PathVariable String username){
        return this.userService.getUserByUsername(username);
    }


}
