package com.example.demo.services;

import com.example.demo.entities.ReservationEntity;
import com.example.demo.entities.TuristicPackageEntity;
import com.example.demo.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    ReservationRepository repository;

    public ReservationEntity createResevation(ReservationEntity newReservation) throws Exception {
        if (newReservation.getPassengers() <= 0){
            throw new Exception("Los pasajeros deben ser mayor a cero. ");
        }
        if (newReservation.getVigency() <= 0){
            throw new Exception("La vigencia de la reserva deber ser mayor a cero días. ");
        }
        if (newReservation.getTuristicPackage().getStatus() == false){
            throw new Exception("No puedes reservar un paquete no disponible. ");
        }
        if (newReservation.getTuristicPackage().getCapacity() < newReservation.getPassengers()){
            throw new Exception("Los pasajeros exceden la capacidad del paquete turístico. ");
        }
        // AGREGAR LÓGICA DE AGENDA DE RESERVA (restar cupos, agregar a cliente, generar pago)
        return repository.save(newReservation);
    }

    public List<ReservationEntity> getReservationHistory(Long userId){
        return repository.findByClient(userId);
    }
    public List<ReservationEntity> findByDate(Date date){
        return repository.findByDate(date);
    }
    public List<ReservationEntity> findByPaid(Boolean paid){
        return repository.findByPaid(paid);
    }
}
