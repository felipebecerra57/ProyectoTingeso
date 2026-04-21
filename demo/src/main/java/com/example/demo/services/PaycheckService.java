package com.example.demo.services;

import com.example.demo.entities.PaycheckEntity;
import com.example.demo.entities.ReservationEntity;
import com.example.demo.repositories.PaycheckRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaycheckService {
    @Autowired
    PaycheckRespository repository;

    public PaycheckEntity calculatePaycheck(ReservationEntity reservation){
        PaycheckEntity paycheck = new PaycheckEntity();
        paycheck.setReservation(reservation);
        paycheck.setDate(LocalDateTime.now());
        double people, client = 0;
        if (reservation.getPassengers() >= 4){
            people = 0.1;}
        if (reservation.getClient().getReservationHistory().size() >= 3) {
            client = 0.1;}
        // APLICAR DEMÁS DESCUENTOOOOS!!!



        return paycheck;
    }

    public List<PaycheckEntity> getAll(){
        return repository.findAll();
    }
    public List<PaycheckEntity> findByMonthYear(int month, int year){
        return repository.findByMonthYear(month, year);
    }
}
