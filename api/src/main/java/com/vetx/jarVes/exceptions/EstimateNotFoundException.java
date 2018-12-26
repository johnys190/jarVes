package com.vetx.jarVes.exceptions;

public class EstimateNotFoundException extends RuntimeException {
    public EstimateNotFoundException(Long id) {
        super("Could not find Estimate" + id);
    }
}
