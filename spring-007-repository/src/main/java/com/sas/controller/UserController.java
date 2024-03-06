package com.sas.controller;


import com.sas.model.User;
import com.sas.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/auth")
public class UserController
{
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<User> findAll () {

        return userRepository.findAll();
    }

    @PostMapping("/register")
    public User getRegister(@RequestBody User register){
        return userRepository.save(register);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById (@PathVariable Long id) {

        userRepository.deleteById(id);
    }

    @GetMapping("/users/{id}")
    public User findById (@PathVariable Long id) {

        return userRepository.findById(id).get();
    }
}
