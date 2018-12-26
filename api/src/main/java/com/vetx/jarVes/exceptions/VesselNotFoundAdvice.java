package com.vetx.jarVes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class VesselNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(VesselNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String VesselOrEstimateNotFoundHandler(VesselNotFoundException ex) {
        return ex.getMessage();
    }
}
