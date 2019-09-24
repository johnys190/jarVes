package com.vetx.jarVes.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserSummary {
  private Long id;
  private String email;
  private String firstName;
  private String lastName;
}
