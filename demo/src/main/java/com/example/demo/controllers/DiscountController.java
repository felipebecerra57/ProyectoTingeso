package com.example.demo.controllers;

import com.example.demo.entities.DiscountEntity;
import com.example.demo.entities.ReservationEntity;
import com.example.demo.services.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/discounts")
@CrossOrigin("*")
public class DiscountController {
    @Autowired
    DiscountService service;

    @PostMapping("/create")
    public ResponseEntity<DiscountEntity> createDiscountPassenger(DiscountEntity discount, Long reservation, Double amount){
        DiscountEntity dis = service.createDiscountPassenger(discount, reservation, amount);
        return ResponseEntity.ok(dis);
    }

    @PostMapping("/create1")
    public ResponseEntity<DiscountEntity> createDiscountClient(DiscountEntity discount, Long reservation, Double amount){
        DiscountEntity dis = service.createDiscountClient(discount, reservation, amount);
        return ResponseEntity.ok(dis);
    }
}
