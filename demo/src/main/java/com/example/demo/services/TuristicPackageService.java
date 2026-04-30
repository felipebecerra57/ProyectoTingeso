package com.example.demo.services;

import com.example.demo.controllers.DTO.TuristicPackageInDTO;
import com.example.demo.controllers.DTO.TuristicPackageOutDTO;
import com.example.demo.entities.TuristicPackageEntity;
import com.example.demo.repositories.TuristicPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TuristicPackageService {
    @Autowired
    TuristicPackageRepository repository;

    public TuristicPackageOutDTO createPackage(TuristicPackageInDTO newPackage)throws Exception{
        TuristicPackageOutDTO DTOout = new TuristicPackageOutDTO();
        TuristicPackageEntity packageEntity = new TuristicPackageEntity();

        // ---- Validate the data from the DTO
        if (newPackage.getName().length() <= 0){
            throw new Exception("El nombre debe tener un largo mayor a cero. ");
        }
        // Destiny length
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

        //----set the attributes from the dto to the entity
        long diffInMillies = Math.abs(newPackage.getFinalDate().getTime() - newPackage.getInicialDate().getTime());
        long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        packageEntity.setDurationDays((int) diffInDays);
        packageEntity.setFromDTO(packageEntity, newPackage);
        repository.save(packageEntity);
        DTOout.setFromEntity(packageEntity, DTOout);
        return DTOout;
    }

    public Boolean deletePackage(Long id){
        try{
            repository.deleteById(id);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public TuristicPackageOutDTO updatePackage(Long id, TuristicPackageInDTO dto){
        TuristicPackageEntity pkg = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paquetito no encontrado"));
        pkg.setFromDTO(pkg, dto);
        repository.save(pkg);
        TuristicPackageOutDTO outdto = new TuristicPackageOutDTO();
        outdto.setFromEntity(pkg, outdto);
        return outdto;
    }

    public List<TuristicPackageEntity> getAllTuristicPackages(){
        return repository.findAll();
    }

    public List<TuristicPackageEntity> findByDestiny(String destiny){
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
