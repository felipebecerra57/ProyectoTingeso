package com.example.demo.entities;

import com.example.demo.controllers.DTO.ReservationInDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "reservations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity client;

    @ManyToMany
    @JoinColumn(name = "turistic_package_id", nullable = false)
    private TuristicPackageEntity turisticPackage;

    private Date date;
    private Integer passengers;
    private Integer vigency; //in days
    private String status;
    private Boolean paid;
    // services
    // conditions

    public ReservationEntity setFromDTO(ReservationEntity reservation, ReservationInDTO reservationDTO){
        //reservation.setTuristicPackage(reservationDTO.getPackagesIds());
        reservation.setDate(reservationDTO.getDate());
        reservation.setPassengers(reservationDTO.getPassengers());
        reservation.setVigency(reservationDTO.getVigencyDays());
        reservation.setStatus(reservationDTO.getStatus());
        reservation.setPaid(reservationDTO.getPaid());
        return reservation;
    }
}
