package com.example.demo.repositories;

import com.example.demo.entities.PaymentDetailEntity;
import com.example.demo.entities.ReservationEntity;
import com.example.demo.services.PaymentDetailService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentDetailRepository extends JpaRepository<PaymentDetailEntity, Long> {
    @Query("SELECT p FROM PaymentDetailEntity p WHERE MONTH(p.date) = :month AND YEAR(p.date) = :year")
    List<PaymentDetailEntity> findByMonthYear(@Param("month") int month, @Param("year") int year);

    @Query("SELECT p FROM PaymentDetailEntity p WHERE YEAR(p.date) = :year")
    List<PaymentDetailEntity> findByYear(@Param("year") int year);
}
