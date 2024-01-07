package com.kushankrishna.SpringSecuriy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/myapp")
public class AppController {


    @GetMapping("/home")
    public ResponseEntity<?> home(){
        return ResponseEntity.status(HttpStatus.OK).body("Welcome to the home page of the application");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody )
}
