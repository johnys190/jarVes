package com.vetx.jarVes.controller;

import com.vetx.jarVes.Payload.ApiResponse;
import com.vetx.jarVes.Payload.JwtAuthenticationResponse;
import com.vetx.jarVes.Payload.LogInRequest;
import com.vetx.jarVes.Payload.SignUpRequest;
import com.vetx.jarVes.model.Role;
import com.vetx.jarVes.model.RoleName;
import com.vetx.jarVes.model.User;
import com.vetx.jarVes.repository.RoleRepository;
import com.vetx.jarVes.repository.UserRepository;
import com.vetx.jarVes.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private AuthenticationManager authenticationManager;

  private UserRepository userRepository;

  private RoleRepository roleRepository;

  private PasswordEncoder passwordEncoder;

  private JwtTokenProvider tokenProvider;

  @Autowired
  public AuthController(AuthenticationManager authenticationManager,
                        UserRepository userRepository,
                        RoleRepository roleRepository,
                        PasswordEncoder passwordEncoder,
                        JwtTokenProvider tokenProvider) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
    this.tokenProvider = tokenProvider;
  }

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LogInRequest loginRequest) {

    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    return ResponseEntity.ok(JwtAuthenticationResponse.builder().accessToken(tokenProvider.generateToken(authentication)).build());
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
          HttpStatus.BAD_REQUEST);
    }

    // Creating user's account
    User user = new User(signUpRequest.getFirstName(), signUpRequest.getLastName(),
        signUpRequest.getEmail(), signUpRequest.getPassword());

    user.setPassword(passwordEncoder.encode(user.getPassword()));

    Role userRole = roleRepository.findByName(RoleName.ROLE_GUEST).get();

    user.setRoles(Collections.singleton(userRole));

    userRepository.save(user);

    return ResponseEntity.ok(new ApiResponse(true, "User registered successfully"));
  }
}