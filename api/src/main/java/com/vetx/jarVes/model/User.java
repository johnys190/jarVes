package com.vetx.jarVes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class User {

    //Variable Declaration
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotBlank(message = "Name is required.")
    @Size(max = 140)
    private String name;

    @NonNull
    @NotBlank(message = "Last name is required.")
    @Size(max = 140)
    private String lastName;

    @NonNull
    @Email
    @NotBlank(message = "Email is required.")
    @Size(max = 140)
    private String email;

    @NonNull
    @NotBlank(message = "Password is required.")
    @Size(max = 140)
    @JsonIgnore
    private String password;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    private Set<Vessel> importantVessels = new HashSet<>();

}
