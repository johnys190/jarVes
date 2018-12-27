package com.vetx.jarVes.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignUpRequest {

  @NotBlank
  @Size(min = 4, max = 100)
  private String firstName;

  @NotBlank
  @Size(min = 3, max = 100)
  private String lastName;

  @NotBlank
  @Size(min = 6, max = 100)
  private String password;

  @Email
  @Size(max = 40)
  @NotBlank
  private String email;
}
