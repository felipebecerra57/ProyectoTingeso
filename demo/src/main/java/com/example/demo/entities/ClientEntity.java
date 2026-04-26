package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "clients")
@Data
public class ClientEntity extends UserEntity{
    @Column(unique = true)
    private String keycloakId;

    private Long phoneNumber;
    private String nationality;
    @OneToMany(mappedBy = "client")
    private List<ReservationEntity> reservationHistory;
}
