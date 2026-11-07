package com.hisabaty.demo.Vehicle;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class VehicleNotFound extends RuntimeException {

    public VehicleNotFound(Long id) {
        super("Error: Vehicle [" + id + " ] not found.");
    }

    public VehicleNotFound() {
        super("Error: Vehicles not found.");
    }

    public VehicleNotFound(String msg) {
        super(msg);
    }
}
