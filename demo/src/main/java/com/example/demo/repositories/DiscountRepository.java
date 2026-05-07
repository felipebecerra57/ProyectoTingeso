package com.example.demo.repositories;

import com.example.demo.entities.DiscountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends JpaRepository<DiscountEntity, Long> {
    DiscountEntity findById(Integer id);
    DiscountEntity findByName(String name);
}
