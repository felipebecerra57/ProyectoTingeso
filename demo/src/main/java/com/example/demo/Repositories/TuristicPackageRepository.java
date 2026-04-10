package com.example.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface TuristicPackageRepository<TuristicPackageEntity> extends JpaRepository<TuristicPackageEntity, Long>{
}
