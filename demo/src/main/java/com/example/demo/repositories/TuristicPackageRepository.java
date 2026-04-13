package com.example.demo.repositories;

import com.example.demo.entities.TuristicPackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TuristicPackageRepository extends JpaRepository<TuristicPackageEntity, Long>{
    List<TuristicPackageEntity> findByDestiny(String destiny);
    List<TuristicPackageEntity> findByPriceLessThan(Float price);
    List<TuristicPackageEntity> findByPriceGreaterThan(Float price);
    List<TuristicPackageEntity> findByCapacityGreaterThan(Integer capacity);
    List<TuristicPackageEntity> findByState(String state);

}
