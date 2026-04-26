package com.example.demo.controllers.DTO;

import com.example.demo.entities.ClientEntity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ReservationInDTO {
    @NotNull
    private Long turisticPackage;

    @NotNull
    private Date date;

    @Min(value = 1, message = "El número de pasajeros debe ser al menos 1")
    private Integer passengers;

    @Min(value = 1, message = "El viaje debe durar mínimo un día")
    @NotNull
    private Integer vigencyDays; //in days
    private String status;
    private Boolean paid;
}
