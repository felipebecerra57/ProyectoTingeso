package com.example.demo.services;

import com.example.demo.entities.TuristicPackageEntity;
import com.example.demo.repositories.TuristicPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TuristicPackageService {
    @Autowired
    TuristicPackageRepository repository;

    public List getAllTuristicPackages(){
        return repository.findAll();
    }

    public List findByDestiny(String destiny){
        return repository.findByDestiny(destiny);
    }

    public List<TuristicPackageEntity> findByPriceLessThan(Float price){
        return repository.findByPriceLessThan(price);
    }
}
