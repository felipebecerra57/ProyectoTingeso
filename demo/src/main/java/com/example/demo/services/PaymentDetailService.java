package com.example.demo.services;

import com.example.demo.entities.PaymentDetailEntity;
import com.example.demo.entities.ReservationEntity;
import com.example.demo.repositories.PaymentDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentDetailService {
    @Autowired
    PaymentDetailRepository repository;

    public PaymentDetailEntity calculatePaymentDetail(ReservationEntity reservation){
        PaymentDetailEntity paymentDetail = new PaymentDetailEntity();
        paymentDetail.setReservation(reservation);
        paymentDetail.setDate(LocalDateTime.now());
        double people, client = 0;
        if (reservation.getPassengers() >= 4){
            people = 0.1;}
        if (reservation.getClient().getReservationHistory().size() >= 3) {
            client = 0.1;}
        // APLICAR DEMÁS DESCUENTOOOOS!!!



        return paymentDetail;
    }
    public List<PaymentDetailEntity> findByMonthYear(int month, int year){
        return repository.findByMonthYear(month, year);
    }
}
