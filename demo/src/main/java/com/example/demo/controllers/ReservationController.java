package com.example.demo.controllers;

import com.example.demo.entities.ReservationEntity;
import com.example.demo.services.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReservationController {
    ReservationService service;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReservationEntity>> getReservationsByUser(Long userId){
        return ResponseEntity.ok(service.getReservationHistory(userId));
    }
}
