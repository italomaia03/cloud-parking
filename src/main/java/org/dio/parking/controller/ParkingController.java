package org.dio.parking.controller;

import org.dio.parking.controller.dto.ParkingDto;
import org.dio.parking.controller.mapper.ParkingMapper;
import org.dio.parking.model.Parking;
import org.dio.parking.service.ParkingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/parking")
public class ParkingController {
    private ParkingMapper parkingMapper;
    private final ParkingService parkingService;

    public ParkingController(ParkingMapper parkingMapper, ParkingService parkingService) {
        this.parkingMapper = parkingMapper;
        this.parkingService = parkingService;
    }

    @GetMapping
    public List<ParkingDto> findAll(){
        List<Parking> parkingList = parkingService.findAll();
        List<ParkingDto> result = parkingMapper.toParkingDtolist(parkingList);
        return result;
    }

}
