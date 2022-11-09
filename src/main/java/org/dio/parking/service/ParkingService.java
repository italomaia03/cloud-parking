package org.dio.parking.service;

import org.dio.parking.model.Parking;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ParkingService {
    private static Map<String, Parking> parkingMap = new HashMap<>();

    static {
        String id = getUUID();
        String id1 = getUUID();
        Parking parking = new Parking(id, "HVK-1260", "CE", "PALIO", "VERMELHO");
        Parking parking1 = new Parking(id1, "POQ-2429", "PB", "FUSCA", "BRANCO");
        parkingMap.put(id, parking);
        parkingMap.put(id1, parking1);
    }

    public List<Parking> findAll(){
        return new ArrayList<>(parkingMap.values());
    }

    //Método usado para geração de ids próprio do Java
    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    public Parking findById(String id) {
        return parkingMap.get(id);
    }

    public Parking create(Parking parkingCreate) {
        String id = getUUID();
        parkingCreate.setId(id);
        parkingCreate.setEntryDate(LocalDateTime.now());
        parkingMap.put(id, parkingCreate);
        return parkingCreate;
    }
}
