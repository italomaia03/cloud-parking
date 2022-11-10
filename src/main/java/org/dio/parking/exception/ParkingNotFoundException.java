package org.dio.parking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//Quando extendemos a partir de RunTime exception, economizamos várias linhas de código
// ao não precisar tratar a exception com estruturas Try/Catch
@ResponseStatus (code = HttpStatus.NOT_FOUND)
public class ParkingNotFoundException extends RuntimeException {

    public ParkingNotFoundException(String id) {
        super("Parking ID not found: " + id);
    }
}
