package com.example.demo.repositories;

import com.example.demo.entities.PaycheckEntity;
import com.example.demo.entities.ReservationEntity;
import com.example.demo.entities.TuristicPackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaycheckRespository extends JpaRepository<PaycheckEntity, Long> {
    @Query("SELECT p FROM PaycheckEntity p WHERE MONTH(p.date) = :month AND YEAR(p.date) = :year")
    List<PaycheckEntity> findByMonthYear(@Param("month") int month, @Param("year") int year);

    @Query("SELECT p FROM PaycheckEntity p WHERE YEAR(p.date) = :year")
    List<PaycheckEntity> findByYear(@Param("year") int year);

    PaycheckEntity calculatePaycheck (ReservationEntity reservation);
}
