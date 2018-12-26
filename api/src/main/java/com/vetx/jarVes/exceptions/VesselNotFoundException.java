package com.vetx.jarVes.exceptions;

public class VesselNotFoundException extends RuntimeException {
    public VesselNotFoundException(Long id) {
        super("Could not find Vessel" + id);
    }
}
