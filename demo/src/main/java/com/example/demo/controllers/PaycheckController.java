package com.example.demo.controllers;

import com.example.demo.entities.PaycheckEntity;
import com.example.demo.entities.ReservationEntity;
import com.example.demo.services.PaycheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/paycheck")
@CrossOrigin("*")
public class PaycheckController {
    @Autowired
    PaycheckService service;

    @GetMapping("/all")
    public ResponseEntity<List<PaycheckEntity>> findAll(){
        List<PaycheckEntity> paychecks = service.getAll();
        return ResponseEntity.ok(paychecks);
    }
    @GetMapping("/findByMonthYear")
    public ResponseEntity<List<PaycheckEntity>> findByMonthYear(@RequestParam("month") int month, @RequestParam("year")int year){
        List<PaycheckEntity> paychecks = service.findByMonthYear(month, year);
        return ResponseEntity.ok(paychecks);
    }
    @PostMapping("/calculate")
    public ResponseEntity<?> calculatePaycheck(@RequestBody ReservationEntity reservation){
        PaycheckEntity paycheck = service.createPaycheck(reservation);
        return ResponseEntity.ok(paycheck);
    }
}
