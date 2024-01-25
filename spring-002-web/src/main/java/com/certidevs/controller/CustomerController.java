package com.certidevs.controller;

import com.certidevs.model.Cliente;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    @GetMapping("/cliente")
    public Cliente customer() {

        return new Cliente(2, "Anita", "Vezuela");

    }


}
