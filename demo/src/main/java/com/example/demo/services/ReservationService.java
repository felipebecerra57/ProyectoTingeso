package com.example.demo.services;

import com.example.demo.entities.ReservationEntity;
import com.example.demo.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    @Autowired
    ReservationRepository repository;

    public List<ReservationEntity> getReservationHistory(Long userId){
        return repository.findByUser(userId);
    }
}
