package com.example.demo.services;

import com.example.demo.controllers.DTO.ReservationInDTO;
import com.example.demo.controllers.DTO.ReservationOutDTO;
import com.example.demo.entities.ClientEntity;
import com.example.demo.entities.ReservationEntity;
import com.example.demo.entities.TuristicPackageEntity;
import com.example.demo.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    ReservationRepository repository;
    public ReservationOutDTO createResevation(ReservationInDTO newReservation) throws Exception {
        ReservationOutDTO DTOout = new ReservationOutDTO();
        ReservationEntity reservationEntity = new ReservationEntity();

        // ---- Validate the data from the DTO
        if (newReservation.getPassengers() <= 0){
            throw new Exception("Los pasajeros deben ser mayor a cero. ");
        }
        if (newReservation.getVigencyDays() <= 0){
            throw new Exception("La vigencia de la reserva deber ser mayor a cero días. ");
        }
        /*
        if (newReservation.getTuristicPackage().getStatus() == false){
            throw new Exception("No puedes reservar un paquete no disponible. ");
        }
        if (newReservation.getTuristicPackage().getCapacity() < newReservation.getPassengers()){
            throw new Exception("Los pasajeros exceden la capacidad del paquete turístico. ");
        }

        // AGREGAR LÓGICA DE AGENDA DE RESERVA (restar cupos, agregar a cliente, generar pago)
        TuristicPackageEntity turisticPackage = newReservation.getTuristicPackage();
        turisticPackage.setCapacity(turisticPackage.getCapacity() - newReservation.getPassengers());

        ClientEntity client = newReservation.getClient();
        client.getReservationHistory().add(newReservation);
         */
        reservationEntity.setFromDTO(reservationEntity, newReservation);
        repository.save(reservationEntity);
        DTOout.setFromEntity(reservationEntity, DTOout);
        return DTOout;
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
