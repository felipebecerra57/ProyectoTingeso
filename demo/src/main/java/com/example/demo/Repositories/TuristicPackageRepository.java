package com.example.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TuristicPackageRepository<TuristicPackageEntity> extends JpaRepository<TuristicPackageEntity, Long>{
    List<TuristicPackageEntity> findByDestiny(String destiny);
    List<TuristicPackageEntity> findByPriceLessThan(Float price);
    List<TuristicPackageEntity> findByPriceGreaterThan(Float price);
    List<TuristicPackageEntity> findByCapacityGreaterThan(Integer capacity);
    List<TuristicPackageEntity> findByStatus(String status);
}
