package com.sas.controller;

import com.sas.model.Login;
import com.sas.repository.LoginRepository;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/auth")
public class LoggingController
{
    private final LoginRepository loginRepository;

    public LoggingController(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @PostMapping("/login")
    public Login getLogin(@RequestBody Login login){
        return loginRepository.save(login);
    }
}
