package com.example.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "TuristicPackage")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TuristicPackageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String name;
    private String destiny;
    private Float price;
    private String description;
    private Date inicialDate;
    private Date finalDate;
    private Integer durationDays;
    private Integer capacity;
    private String state;
    private List<String> services;
    private List<String> conditions;
    private List<String> restrictions;


}
