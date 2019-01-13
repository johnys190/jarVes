package com.vetx.jarVes.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long key) {
        super("Could not find User" + key);
    }
}
