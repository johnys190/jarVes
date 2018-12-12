package com.vetx.jarVes.Payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

public class LogInRequest {

    @Data
    public class LoginRequest {
        @NotBlank
        private String email;

        @NotBlank
        private String password;
    }
}
