package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    private UserEntity client;

    @ManyToOne
    @JoinColumn(name = "turistic_package_id", nullable = false)
    private TuristicPackageEntity turisticPackage;

    private Date date;
    private Integer passengers;
    private Integer vigency; //in days
    private Boolean paid;
    // services
    // conditions

}
