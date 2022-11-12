package org.dio.parking.service;

import org.dio.parking.exception.ParkingNotFoundException;
import org.dio.parking.model.Parking;
import org.dio.parking.repository.ParkingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ParkingService {

    private final ParkingRepository parkingRepository;

    //Método usado para geração de ids próprio do Java
    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    @Transactional(readOnly = true)
    public List<Parking> findAll(){
        return parkingRepository.findAll();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Parking findById(String id) {
        return parkingRepository.findById(id).orElseThrow(() -> new ParkingNotFoundException(id));
    }

    @Transactional
    public Parking create(Parking parkingCreate) {
        String id = getUUID();
        parkingCreate.setId(id);
        parkingCreate.setEntryDate(LocalDateTime.now());
        parkingRepository.save(parkingCreate);
        return parkingCreate;
    }
    @Transactional
    public void delete(String id) {
        findById(id);
        parkingRepository.deleteById(id);
    }
    @Transactional
    public Parking update(String id, Parking parkingUpdate) {
        Parking parking = findById(id);
        parking.setColor(parkingUpdate.getColor());
        parking.setState(parkingUpdate.getState());
        parking.setLicense(parkingUpdate.getLicense());
        parking.setModel(parkingUpdate.getModel());
        parkingRepository.save(parking);
        return parking;
    }

    @Transactional
    public Parking checkOut(String id) {
        Parking parking = findById(id);
        parking.setExitDate(LocalDateTime.now());
        double bill = ParkingCheckOut.getBill(parking);
        parking.setBill(bill);
        parkingRepository.save(parking);
        return parking;
    }
}
