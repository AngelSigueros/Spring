package com.sas.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PruebaLoggerController {

    @GetMapping("/")
    public void findAll() {
        log.info("findAll called");
        log.debug("findAll called");
        log.warn("findAll called");
        log.error("findAll called");
    }
}
