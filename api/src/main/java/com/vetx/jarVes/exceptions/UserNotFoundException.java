package com.vetx.jarVes.exceptions;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(Long id) {
    super("Could not find Vessel" + id);
  }
}
