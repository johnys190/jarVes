package com.vetx.jarVes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT)  // 409
public class VesselAlreadyExistsException extends RuntimeException {
    public VesselAlreadyExistsException(Long key) {
        super("Vessel with key: " + key + "already exists.");
    }
}
