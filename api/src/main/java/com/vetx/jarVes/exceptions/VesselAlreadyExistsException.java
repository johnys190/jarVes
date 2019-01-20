package com.vetx.jarVes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT)  // 409
public class VesselAlreadyExistsException extends RuntimeException {
    public VesselAlreadyExistsException(String name) {
        super("Vessel with name: " + name + "already exists.");
    }
}
