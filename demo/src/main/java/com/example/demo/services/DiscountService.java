package com.example.demo.services;

import com.example.demo.entities.DiscountEntity;
import com.example.demo.entities.ReservationEntity;
import com.example.demo.repositories.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
public class DiscountService {
    @Autowired
    DiscountRepository repository;


    public DiscountEntity createDiscountPassenger(DiscountEntity discount, Long reservation, Double amount){
        discount.setName("Pasajeros");
        discount.setDescription("Descuento por muchos pasajeros");
        discount.setAmount(0.1);
        discount.setReservation(reservation);
        double savedAmount = amount * 0.1;
        discount.setSavedAmount(amount * 0.1);
        repository.save(discount);
        return discount;
    }

    public DiscountEntity createDiscountClient(DiscountEntity discount, Long reservation, Double amount){
        discount.setName("Cliente");
        discount.setDescription("Descuento por muchas reservas");
        discount.setAmount(0.1);
        discount.setReservation(reservation);
        double savedAmount = amount * 0.1;
        discount.setSavedAmount(amount * 0.1);
        repository.save(discount);
        return discount;
    }
}
