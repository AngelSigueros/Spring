package com.sas.controller;

import com.sas.model.User;
import com.sas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        return userRepository.findById(id).orElse(new User());
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        // Opcional, se puede optar por comprobar el address si existe
        // si no existe se puede crear con addressRepo.save(user.getAddress()) primero

        try {
            return ResponseEntity.ok(userRepository.save(user));
        } catch (Exception e) {// capturar errores derivados de guardar en db
            return ResponseEntity.status(409).build(); // No se puede guardar por conflicto con otros usuarios/address
        }
    }

    @PutMapping("{id}")
    public User update(@PathVariable Long id, @RequestBody User user) {
        Optional<User> userOpt = userRepository.findById(id);
        if(userOpt.isEmpty()) // Si isEmpty significa que no existe y por tanto devolvemos 404 not found
            return null;

        User userFromDB = userOpt.get();

        userFromDB.setEmail(user.getEmail());


        return userRepository.save(userFromDB);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Long id) {
        userRepository.deleteById(id);
    }




}
