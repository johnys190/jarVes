package com.vetx.jarVes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class VesselNotFoundException extends RuntimeException {
    public VesselNotFoundException(Long id) {
        super("Could not find Vessel" + id);
    }
}
