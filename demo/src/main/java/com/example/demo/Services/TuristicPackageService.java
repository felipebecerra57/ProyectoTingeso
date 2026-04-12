package com.example.demo.Services;

import com.example.demo.Repositories.TuristicPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TuristicPackageService {
    @Autowired
    TuristicPackageRepository turisticPackageRepository;

    public List getAllTuristicPackages(){
        return turisticPackageRepository.findAll();
    }
    public List findByDestiny(String destiny){
        return turisticPackageRepository.findByDestiny(destiny);
    }
}
