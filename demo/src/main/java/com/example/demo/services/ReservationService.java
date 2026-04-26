package com.example.demo.services;

import com.example.demo.controllers.DTO.ReservationInDTO;
import com.example.demo.controllers.DTO.ReservationOutDTO;
import com.example.demo.entities.ClientEntity;
import com.example.demo.entities.PaymentDetailEntity;
import com.example.demo.entities.ReservationEntity;
import com.example.demo.entities.TuristicPackageEntity;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.repositories.PaymentDetailRepository;
import com.example.demo.repositories.ReservationRepository;
import com.example.demo.repositories.TuristicPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    ReservationRepository repository;
    @Autowired
    TuristicPackageRepository packageRepository;
    @Autowired
    PaymentDetailRepository paymentDetailRepository;
    @Autowired
    ClientRepository clientRepository;

    public ReservationOutDTO createResevation(ReservationInDTO newReservation) throws Exception {
        ReservationOutDTO DTOout = new ReservationOutDTO();
        ReservationEntity reservationEntity = new ReservationEntity();
        String keycloakId = SecurityContextHolder.getContext().getAuthentication().getName();
        ClientEntity client = clientRepository.findByKeycloakId(keycloakId);

        // ---- Validate the data from the DTO
        if (newReservation.getPassengers() <= 0){
            throw new Exception("Los pasajeros deben ser mayor a cero. ");
        }
        if (newReservation.getVigencyDays() <= 0){
            throw new Exception("La vigencia de la reserva deber ser mayor a cero días. ");
        }
        // Validate the data of all the packages in the reservation
        TuristicPackageEntity turisticPackage = packageRepository.findById(newReservation.getTuristicPackage())
                .orElseThrow(() -> new Exception("Paquete no encontrado"));
        if(turisticPackage.getCapacity() < newReservation.getPassengers()){
            throw new Exception("Cupos insuficientes en el paquete: " + turisticPackage.getName());
        }
        if(!turisticPackage.getStatus()){
            throw new Exception("El paquete " + turisticPackage.getName() + " no está activo");
        }
        Integer newCapacity = turisticPackage.getCapacity() - newReservation.getPassengers();
        if(newCapacity == 0){
            turisticPackage.setCapacity(newCapacity);
            turisticPackage.setStatus(false);
        }
        if(newCapacity > 0){
            turisticPackage.setCapacity(newCapacity);
        }
        packageRepository.save(turisticPackage);
        reservationEntity.setFromDTO(reservationEntity, newReservation);
        reservationEntity.setClient(client);
        newReservation.setPaid(false);
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
