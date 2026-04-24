package com.example.demo.controllers;

import com.example.demo.entities.PaymentDetailEntity;
import com.example.demo.entities.ReservationEntity;
import com.example.demo.repositories.PaymentDetailRepository;
import com.example.demo.services.PaymentDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/paycheck")
@CrossOrigin("*")
public class PaymentDetailController {
    @Autowired
    PaymentDetailService service;

    @GetMapping("/findByMonthYear")
    public ResponseEntity<List<PaymentDetailEntity>> findByMonthYear(@RequestParam("month") int month, @RequestParam("year")int year){
        List<PaymentDetailEntity> paymentDetails= service.findByMonthYear(month, year);
        return ResponseEntity.ok(paymentDetails);
    }
    @PostMapping("/calculate")
    public ResponseEntity<?> calculatePaycheck(@RequestBody ReservationEntity reservation){
        PaymentDetailEntity paymentDetails = service.calculatePaymentDetail(reservation);
        return ResponseEntity.ok(paymentDetails);
    }
}
