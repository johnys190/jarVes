package com.vetx.jarVes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class User {

    //Variable Declaration
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required.")
    @Size(max = 140)
    private String name;

    @NotBlank(message = "Last firstName is required.")
    @Size(min = 3, max = 140, message = "Last firstName is required")
    private String lastName;

    @Email
    @NotBlank(message = "Email is required.")
    @Size(max = 140)
    private String email;

    @NotBlank(message = "Password is required.")
    @Size(max = 140)
    @JsonIgnore
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}
