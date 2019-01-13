package com.vetx.jarVes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)  // 404
public class VesselAlreadyExistsException extends RuntimeException {
    public VesselAlreadyExistsException(Long key) {
        super("Could not find Estimate" + key);
    }
}
