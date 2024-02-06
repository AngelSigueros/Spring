package com.sas.sm_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
@CrossOrigin(origins = "http://localhost:4200")
@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "home/index";
    }

}
