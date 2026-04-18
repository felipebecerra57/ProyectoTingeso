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

    public TuristicPackageEntity createPackage(TuristicPackageEntity newPackage)throws Exception{
        //Valid name length
        if (newPackage.getName().length() <= 0){
            throw new Exception("El nombre debe tener un largo mayor a cero. ");
        }
        if (newPackage.getDestiny().length() <= 0){
            throw new Exception("El destino debe tener un largo mayor a cero. ");
        }
        // Valid Date
        if (newPackage.getFinalDate().before(newPackage.getInicialDate())){
            throw new Exception("La fecha de término debe ser mayor a la de inicio. ");
        }
        // Price must be greater than zero
        if (newPackage.getPrice() <= 0){
            throw new Exception("El precio debe ser mayor a cero. ");
        }
        // Capacity must be greater than zero
        if (newPackage.getCapacity() <= 0){
            throw new Exception("Los cupos deben ser mayor a cero. ");
        }
        if (newPackage.getDurationDays() <= 0){
            throw new Exception("La duración deber mayor a cero días. ");
        }
        return repository.save(newPackage);
    }

    public List getAllTuristicPackages(){
        return repository.findAll();
    }

    public List findByDestiny(String destiny){
        return repository.findByDestiny(destiny);
    }

    public List<TuristicPackageEntity> findByPriceLessThan(Float price){
        return repository.findByPriceLessThan(price);
    }
    public List<TuristicPackageEntity> findByCapacity(Integer capacity){
        return repository.findByCapacityGreaterThan(capacity);
    }
    public List<TuristicPackageEntity> findByStatus(String status){
        return repository.findByStatus(status);
    }
    public List<TuristicPackageEntity> findByDurationDays(Integer duration){
        return repository.findByDurationDaysGreaterThan(duration);
    }
}
