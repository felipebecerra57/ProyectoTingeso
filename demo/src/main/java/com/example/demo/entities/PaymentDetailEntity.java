package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "paymentDetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetailEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "reservation", nullable = false)
    private ReservationEntity reservation;

    private LocalDateTime date;
    private Double amount;
}
