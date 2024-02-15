package com.sas.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
ENTRAR EN:
http://localhost:8080/swagger-ui/index.html
*/

@RestController
public class HelloController {

    @GetMapping("/hola")
    public String hello() {
        return "Holaaa!!!";
    }

    @GetMapping("/enga")
    public String enga() {
        return "Engaa!!!";
    }

    @GetMapping("/adios")
    public String adios() {
        return "Adioosss!!!";
    }
}
