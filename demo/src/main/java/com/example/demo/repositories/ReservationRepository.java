package com.example.demo.repositories;

import com.example.demo.entities.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    List<ReservationEntity> findByClient(Long client_id);
    List<ReservationEntity> findByDate(Date date);
    List<ReservationEntity> findByPaid(Boolean paid);
}
