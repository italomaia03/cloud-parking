package org.dio.parking.controller.mapper;

import org.dio.parking.controller.dto.ParkingDto;
import org.dio.parking.model.Parking;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ParkingMapper {

    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    public ParkingDto parkingDto(Parking parking){
        return MODEL_MAPPER.map(parking, ParkingDto.class);
    }
    public List<ParkingDto> toParkingDtolist(List<Parking> parkingList) {
        return parkingList.stream().map(this :: parkingDto).collect(Collectors.toList());
    }
}
