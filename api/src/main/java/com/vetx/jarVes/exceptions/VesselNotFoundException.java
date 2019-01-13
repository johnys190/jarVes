package com.vetx.jarVes.exceptions;

public class VesselNotFoundException extends RuntimeException {
    public VesselNotFoundException(Long key) {
        super("Could not find Vessel" + key);
    }
}
