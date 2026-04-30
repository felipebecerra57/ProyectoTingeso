package com.example.demo.controllers.DTO;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class TuristicPackageInDTO {
    private String name;
    private String destiny;
    private Float price;
    private String description;
    private Date inicialDate;
    private Date finalDate;
    private Integer durationDays;
    private Integer capacity;
    private Boolean status = true;
    private List<String> services;
    private List<String> conditions;

}
