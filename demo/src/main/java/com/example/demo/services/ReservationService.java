package com.example.demo.services;

import com.example.demo.controllers.DTO.ReservationInDTO;
import com.example.demo.controllers.DTO.ReservationOutDTO;
import com.example.demo.entities.*;
import com.example.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    ReservationRepository repository;
    @Autowired
    TuristicPackageRepository packageRepository;
    @Autowired
    PaymentDetailRepository paymentDetailRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    DiscountRepository discountRepository;
    @Autowired
    DiscountService discountService;

    public ReservationEntity setFromDTO(ReservationEntity reservation, ReservationInDTO reservationDTO){
        TuristicPackageEntity pkg = packageRepository.findById(reservationDTO.getTuristicPackage())
                .orElseThrow(() -> new RuntimeException("Paquete no encontrado con ID: " ));
        reservation.setTuristicPackage(pkg);
        reservation.setDate(reservationDTO.getDate());
        reservation.setPassengers(reservationDTO.getPassengers());
        //reservation.setVigency(reservationDTO.getVigencyDays());
        reservation.setStatus(reservationDTO.getStatus());
        reservation.setPaid(reservationDTO.getPaid());
        return reservation;
    }

    private ClientEntity getOrCreateClient(String keycloakId) {
        // First we look for the client
        ClientEntity client = clientRepository.findByKeycloakId(keycloakId);
        // if they not exists, we create it
        if (client == null) {
            client = new ClientEntity();
            client.setKeycloakId(keycloakId);
            client.setNationality("No especificada");
            //client.setPhoneNumber(0L);
            client.setAccountState(true);
            client = clientRepository.save(client);
        }
        return client;
    }

    public ReservationOutDTO createResevation(ReservationInDTO newReservation) throws Exception {
        ReservationOutDTO DTOout = new ReservationOutDTO();
        ReservationEntity reservationEntity = new ReservationEntity();
        String keycloakId = newReservation.getClient();
        ClientEntity client = getOrCreateClient(keycloakId);

        // ---- Validate the data from the DTO
        if (newReservation.getPassengers() <= 0){
            throw new Exception("Los pasajeros deben ser mayor a cero. ");
        }
        // Validate the data of all the packages in the reservation
        TuristicPackageEntity turisticPackage = packageRepository.findById(newReservation.getTuristicPackage())
                .orElseThrow(() -> new Exception("Paquete no encontrado"));

        if(turisticPackage.getCapacity() < newReservation.getPassengers()){
            throw new Exception("Cupos insuficientes en el paquete: " + turisticPackage.getName());
        }
        if(!turisticPackage.getStatus()){
            throw new Exception("El paquete " + turisticPackage.getName() + " no está activo");
        }
        //---- Capacity and status
        int newCapacity = turisticPackage.getCapacity() - newReservation.getPassengers();
        if(newCapacity == 0){
            turisticPackage.setCapacity(newCapacity);
            turisticPackage.setStatus(false);
        }
        if(newCapacity > 0){
            turisticPackage.setCapacity(newCapacity);
        }
        //---- Final price with discounts
        double basePrice = newReservation.getPassengers() * turisticPackage.getPrice();
        List<DiscountEntity> discounts = new ArrayList<>();

        // passengers discount: if passengers >= 3
        if (newReservation.getPassengers() >= 3) {
            DiscountEntity dis = new DiscountEntity();
            Long id = reservationEntity.getId();
            dis = discountService.createDiscountPassenger(dis, id, basePrice);
            discounts.add(dis);
        }
        // frequent client: if client has 3 or more reservations
        if (client.getReservationHistory() != null && client.getReservationHistory().size() >= 3) {
            DiscountEntity dis = new DiscountEntity();
            Long id = reservationEntity.getId();
            dis = discountService.createDiscountClient(dis, id, basePrice);
            discounts.add(dis);
        }
        double totalDiscount = 0.0;
        for (DiscountEntity e :discounts){
            totalDiscount += basePrice * e.getAmount();
        }
        double finalAmount = basePrice - totalDiscount;
        reservationEntity.setDiscounts(discounts);
        reservationEntity.setFinalAmount(finalAmount);


        packageRepository.save(turisticPackage);
        reservationEntity = setFromDTO(reservationEntity, newReservation);
        reservationEntity.setClient(client);
        newReservation.setPaid(false);
        repository.save(reservationEntity);
        DTOout.setFromEntity(reservationEntity, DTOout);
        //DTOout.setOriginalPrice(basePrice);
        return DTOout;
    }

    public ReservationOutDTO simulateReservation(ReservationInDTO newReservation) {
        TuristicPackageEntity pkg = packageRepository.findById(newReservation.getTuristicPackage())
                .orElseThrow(() -> new RuntimeException("Paquete no encontrado"));
        String keycloakId = newReservation.getClient();
        ClientEntity client = getOrCreateClient(keycloakId);

        double basePrice = newReservation.getPassengers() * pkg.getPrice();
        List<DiscountEntity> discounts = new ArrayList<>();

        if (newReservation.getPassengers() >= 3) {

        }
        // frequent client: if client has 3 or more reservations
        if (client.getReservationHistory() != null && client.getReservationHistory().size() >= 3) {
            discounts.add(discountRepository.findById(1));
        }
        double totalDiscount = 0;
        for (DiscountEntity d : discounts) {
            totalDiscount += basePrice * d.getAmount();
        }
        double finalAmount = basePrice - totalDiscount;

        ReservationOutDTO DTOut = new ReservationOutDTO();
        DTOut.setOriginalPrice(basePrice);
        DTOut.setFinalPrice(finalAmount);
        DTOut.setDiscounts(discounts);

        return DTOut;
    }

    public List<ReservationOutDTO> getClientReservations() {
        String keycloakId = SecurityContextHolder.getContext().getAuthentication().getName();
        ClientEntity client = clientRepository.findByKeycloakId(keycloakId);
        if (client == null) return new ArrayList<>();

        List<ReservationOutDTO> reservationsDTO = new ArrayList<>();
        List<ReservationEntity> reservations = client.getReservationHistory();
        for (ReservationEntity e : reservations){
            ReservationOutDTO dto = new ReservationOutDTO();
            dto.setFromEntity(e,dto);
            reservationsDTO.add(dto);
        }
        return reservationsDTO;
    }


    public List<ReservationEntity> getReservationHistory(Long userId){
        return repository.findByClient(userId);
    }
    public List<ReservationEntity> findByDate(Date date){
        return repository.findByDate(date);
    }
    public List<ReservationEntity> findByPaid(Boolean paid){
        return repository.findByPaid(paid);
    }
}
