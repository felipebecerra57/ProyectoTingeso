package com.example.demo.controllers.DTO;

import com.example.demo.entities.TuristicPackageEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class TuristicPackageOutDTO {
    private String name;
    private String destiny;
    private Float price;
    private String description;
    private Date inicialDate;
    private Date finalDate;
    private Integer durationDays;
    private Integer capacity;
    private Boolean status;
    private List<String> services;
    private List<String> conditions;

    public TuristicPackageOutDTO setFromEntity(TuristicPackageEntity packageDTO, TuristicPackageOutDTO turisticPackage){
        turisticPackage.setName(packageDTO.getName());
        turisticPackage.setDestiny(packageDTO.getDestiny());
        turisticPackage.setPrice(packageDTO.getPrice());
        turisticPackage.setDescription(packageDTO.getDescription());
        turisticPackage.setInicialDate(packageDTO.getInicialDate());
        turisticPackage.setFinalDate(packageDTO.getFinalDate());
        turisticPackage.setDurationDays(packageDTO.getDurationDays());
        turisticPackage.setCapacity(packageDTO.getCapacity());
        turisticPackage.setStatus(packageDTO.getStatus());
        turisticPackage.setServices(packageDTO.getServices());
        return turisticPackage;
    }
}
