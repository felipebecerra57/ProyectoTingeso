package com.example.demo.controllers;

import com.example.demo.entities.ReservationEntity;
import com.example.demo.services.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin("*")
public class ReservationController {
    ReservationService service;

    @GetMapping("/findByUser/{userId}")
    public ResponseEntity<List<ReservationEntity>> getReservationsByUser(@PathVariable Long userId){
        return ResponseEntity.ok(service.getReservationHistory(userId));
    }
    @GetMapping("/findByDate/{date}")
    public ResponseEntity<List<ReservationEntity>> findByDate(Date date){
        List<ReservationEntity> reservations = service.findByDate(date);
        return ResponseEntity.ok(reservations);
    }
    @GetMapping("/findByPaid/{paid}")
    public ResponseEntity<List<ReservationEntity>> findByPaid(Boolean paid){
        List<ReservationEntity> reservations = service.findByPaid(paid);
        return ResponseEntity.ok(reservations);
    }
}
