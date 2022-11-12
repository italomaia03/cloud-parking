package org.dio.parking.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dio.parking.controller.dto.ParkingCreateDto;
import org.dio.parking.controller.dto.ParkingDto;
import org.dio.parking.controller.mapper.ParkingMapper;
import org.dio.parking.model.Parking;
import org.dio.parking.service.ParkingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking")
@Api(tags = "Parking Controller")
public class ParkingController {
    private ParkingMapper parkingMapper;
    private final ParkingService parkingService;

    public ParkingController(ParkingMapper parkingMapper, ParkingService parkingService) {
        this.parkingMapper = parkingMapper;
        this.parkingService = parkingService;
    }

    @GetMapping
    @ApiOperation("Find all registered parkings")
    public ResponseEntity <List<ParkingDto>> findAll(){
        List<Parking> parkingList = parkingService.findAll();
        List<ParkingDto> result = parkingMapper.toParkingDtolist(parkingList);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/{id}")
    @ApiOperation("Find a parking by passing its ID")
    public ResponseEntity<ParkingDto> findById(@PathVariable String id) {
        Parking parking = parkingService.findById(id);
        ParkingDto result = parkingMapper.toParkingDto(parking);
        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/{id}")
    @ApiOperation("Delete a parking by passing its ID")
    public ResponseEntity delete(@PathVariable String id){
        parkingService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping
    @ApiOperation("Create a parking registry")
    public ResponseEntity<ParkingDto> create(@RequestBody ParkingCreateDto dto){
        var parkingCreate = parkingMapper.toParkingCreate(dto);
        var parking = parkingService.create(parkingCreate);
        ParkingDto result = parkingMapper.toParkingDto(parking);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
    @PutMapping("/{id}")
    @ApiOperation("Update a parking registry")
    public ResponseEntity<ParkingDto> update(@PathVariable String id, @RequestBody ParkingCreateDto dto){
        var parkingCreate = parkingMapper.toParkingCreate(dto);
        var parking = parkingService.update(id, parkingCreate);
        ParkingDto result = parkingMapper.toParkingDto(parking);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    @PostMapping("/{id}")
    @ApiOperation("Check-out and get the bill")
    public ResponseEntity checkOut(@PathVariable String id){
        Parking parking = parkingService.checkOut(id);
        return ResponseEntity.ok(parkingMapper.toParkingDto(parking));
    }
}
