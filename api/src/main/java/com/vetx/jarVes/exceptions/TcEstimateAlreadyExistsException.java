package com.vetx.jarVes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT)  // 409
public class TcEstimateAlreadyExistsException extends RuntimeException {
    public TcEstimateAlreadyExistsException(String name) {
        super("The Time Charter Estimate with name: " + name + ", already exists");
    }
}