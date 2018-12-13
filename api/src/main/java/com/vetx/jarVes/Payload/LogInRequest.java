package com.vetx.jarVes.Payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LogInRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
