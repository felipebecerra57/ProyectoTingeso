package com.example.demo.controllers;

import com.example.demo.controllers.DTO.PaymentDetailDTO;
import com.example.demo.controllers.DTO.ReservationInDTO;
import com.example.demo.controllers.DTO.ReservationOutDTO;
import com.example.demo.entities.ReservationEntity;
import com.example.demo.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin("*")
public class ReservationController {
    @Autowired
    ReservationService service;

    @GetMapping("allReservations")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReservationEntity>> getAllReservations() {
        return ResponseEntity.ok(service.findAll());}

    @PreAuthorize("hasAnyRole('Client','Admin')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ReservationInDTO reservation){
        try{
            ReservationOutDTO saveReservation = service.createResevation(reservation);
            return ResponseEntity.status(HttpStatus.CREATED).body("Reserva creada exitósamente");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());}
    }

    @PostMapping("/simulate")
    public ResponseEntity<ReservationOutDTO> simulate(@RequestBody ReservationInDTO dto) {
        return ResponseEntity.ok(service.simulateReservation(dto));
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping("/findByUser/{userId}")
    public ResponseEntity<List<ReservationEntity>> getReservationsByUser(@PathVariable Long userId){
        return ResponseEntity.ok(service.getReservationHistory(userId));
    }
    @PreAuthorize("hasRole('Admin')")
    @GetMapping("/findByDate/{date}")
    public ResponseEntity<List<ReservationEntity>> findByDate(Date date){
        List<ReservationEntity> reservations = service.findByDate(date);
        return ResponseEntity.ok(reservations);
    }
    @PreAuthorize("hasRole('Admin')")
    @GetMapping("/findByPaid/{paid}")
    public ResponseEntity<List<ReservationEntity>> findByPaid(Boolean paid){
        List<ReservationEntity> reservations = service.findByPaid(paid);
        return ResponseEntity.ok(reservations);
    }
    @GetMapping("/myReservations")
    public ResponseEntity<List<ReservationOutDTO>> getMyReservations() {
        return ResponseEntity.ok(service.getClientReservations());
    }
    @PutMapping("/{id}/pay")
    public ResponseEntity<PaymentDetailDTO> payReservation(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        String method = (String) request.get("paymentMethod");
        System.out.println("Método de pago recibido: " + method);
        return ResponseEntity.ok(service.payReservation(id, method));
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> deleteReservation(@PathVariable Long id){
        service.deleteReservation(id);
        return ResponseEntity.ok().build();
    }
}
