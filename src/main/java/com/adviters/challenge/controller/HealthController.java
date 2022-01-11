package com.adviters.challenge.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/")
public class HealthController {

    @GetMapping()
    public String health() {
        return "Welcome to App.";
    }
}
