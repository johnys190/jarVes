package com.vetx.jarVes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT)  // 409
public class VoyEstimateAlreadyExistsException extends RuntimeException {
    public VoyEstimateAlreadyExistsException(String name) {
        super("The Voyage Estimate with name: " + name + ", already exists");
    }
}