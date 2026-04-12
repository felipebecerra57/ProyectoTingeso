package com.example.demo.Controllers;

import com.example.demo.Entities.TuristicPackageEntity;
import com.example.demo.Services.TuristicPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/turisticPackages")
public class TuristicPackageController {

    @Autowired
    private TuristicPackageService service;

    @GetMapping("/")
    public ResponseEntity<List<TuristicPackageEntity>> listTuristicPackages(){
        List<TuristicPackageEntity> packages = service.getAllTuristicPackages();
        return ResponseEntity.ok(packages);
    }
}
