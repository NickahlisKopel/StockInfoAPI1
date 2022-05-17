package com.careerdevs.stockinfoapi.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping("/")
    public ResponseEntity<?> rootRoute(){
        //return new ResponseEntity<>("Root Route", HttpStatus.OK);
        return ResponseEntity.ok("Root Route");
    }

}
