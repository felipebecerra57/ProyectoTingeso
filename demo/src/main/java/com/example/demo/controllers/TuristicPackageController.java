package com.example.demo.controllers;

import com.example.demo.entities.TuristicPackageEntity;
import com.example.demo.services.TuristicPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/turisticPackages")
public class TuristicPackageController {

    @Autowired
    private TuristicPackageService service;

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody TuristicPackageEntity turisticPackage){
        try{
            TuristicPackageEntity savePackage = service.createPackage(turisticPackage);
            return ResponseEntity.status(HttpStatus.CREATED).body(":p");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<TuristicPackageEntity>> listTuristicPackages(){
        List<TuristicPackageEntity> packages = service.getAllTuristicPackages();
        return ResponseEntity.ok(packages);
    }
    @GetMapping("/search")
    public ResponseEntity<List<TuristicPackageEntity>>searchByDestiny(@RequestParam String destiny){
        List<TuristicPackageEntity> packages = service.findByDestiny(destiny);
        return ResponseEntity.ok(packages);
    }
    @GetMapping("/filter")
    public ResponseEntity<List<TuristicPackageEntity>> filterByPriceLess(@RequestParam Float price){
        List<TuristicPackageEntity> packages = service.findByPriceLessThan(price);
        return ResponseEntity.ok(packages);
    }
    @GetMapping("/filterCapacity")
    public ResponseEntity<List<TuristicPackageEntity>> findByCapacityGreaterThan(@RequestParam Integer capacity){
        List<TuristicPackageEntity> packages = service.findByCapacity(capacity);
        return ResponseEntity.ok(packages);
    }
    @GetMapping("/filterStatus")
    public ResponseEntity<List<TuristicPackageEntity>> findByState(@RequestParam String status){
        List<TuristicPackageEntity> packages = service.findByStatus(status);
        return ResponseEntity.ok(packages);
    }
}
