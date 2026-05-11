package com.example.demo.controllers.DTO;

import com.example.demo.entities.PaymentDetailEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentDetailDTO {
    private LocalDateTime date;
    private Double amount;
    private String paymentMethod;
    private String PackageName;
}
