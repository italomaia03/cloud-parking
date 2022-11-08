package org.dio.parking.service;

import org.dio.parking.model.Parking;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ParkingService {
    private static Map<String, Parking> parkingMap = new HashMap<>();

    static {
        String id = getUUID();
        Parking parking = new Parking(id, "HVK-1260", "CE", "PALIO", "VERMELHO");
        parkingMap.put(id, parking);
    }

    public List<Parking> findAll(){
        return new ArrayList<>(parkingMap.values());
    }

    //Método usado para geração de ids próprio do Java
    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }


}
