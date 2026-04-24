package com.example.demo.controllers.DTO;

import com.example.demo.entities.ReservationEntity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
@Data
public class ReservationOutDTO {
    private Date date;
    @Min(value = 1, message = "El número de pasajeros debe ser al menos 1")
    private Integer passengers;

    @Min(value = 1, message = "El viaje debe durar mínimo un día")
    @NotNull
    private Integer vigencyDays; //in days
    private String status;
    private Boolean paid;

    public ReservationOutDTO setFromEntity(ReservationEntity reservationDTO, ReservationOutDTO reservation){
        reservation.setDate(reservationDTO.getDate());
        reservation.setPassengers(reservationDTO.getPassengers());
        reservation.setVigencyDays(reservationDTO.getVigency());
        reservation.setStatus(reservationDTO.getStatus());
        reservation.setPaid(reservationDTO.getPaid());
        return reservation;
    }
}
