package com.example.demo.controllers.DTO;

import com.example.demo.entities.DiscountEntity;
import com.example.demo.entities.ReservationEntity;
import com.example.demo.entities.TuristicPackageEntity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ReservationOutDTO {
    @NotNull
    private String turisticPackage;

    @NotNull
    private String destiny;

    private Date date;
    @Min(value = 1, message = "El número de pasajeros debe ser al menos 1")
    private Integer passengers;

    //@Min(value = 1, message = "El viaje debe durar mínimo un día")
    //@NotNull
    //private Integer vigencyDays; //in days
    private String status;
    private Boolean paid;
    private List<DiscountEntity> discounts;
    private Double originalPrice;
    private Double finalPrice;


    public ReservationOutDTO setFromEntity(ReservationEntity reservationDTO, ReservationOutDTO reservation){
        reservation.setTuristicPackage(reservationDTO.getTuristicPackage().getName());
        reservation.setDestiny(reservationDTO.getTuristicPackage().getDestiny());
        reservation.setDate(reservationDTO.getDate());
        reservation.setPassengers(reservationDTO.getPassengers());
        //reservation.setVigencyDays(reservationDTO.getVigency());
        reservation.setStatus(reservationDTO.getStatus());
        reservation.setPaid(reservationDTO.getPaid());
        reservation.setDiscounts(reservationDTO.getDiscounts());
        reservation.setFinalPrice(reservationDTO.getFinalAmount());
        return reservation;
    }
}
