package com.example.demo.controllers;

import com.example.demo.entities.TuristicPackageEntity;
import com.example.demo.services.TuristicPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/turisticPackages")
public class TuristicPackageController {

    @Autowired
    private TuristicPackageService service;

    @GetMapping("/all")
    public ResponseEntity<List<TuristicPackageEntity>> listTuristicPackages(){
        List<TuristicPackageEntity> packages = service.getAllTuristicPackages();
        return ResponseEntity.ok(packages);
    }
    @GetMapping("/search")
    public ResponseEntity<List<TuristicPackageEntity>>search(@RequestParam String destiny){
        List<TuristicPackageEntity> packages = service.findByDestiny(destiny);
        return ResponseEntity.ok(packages);
    }
    @GetMapping("/filter")
    public ResponseEntity<List<TuristicPackageEntity>> filterByPrice(@RequestParam Float price){
        List<TuristicPackageEntity> packages = service.findByPriceLessThan(price);
        return ResponseEntity.ok(packages);
    }
}
