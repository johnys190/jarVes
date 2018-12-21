package com.vetx.jarVes.controller;

import com.vetx.jarVes.payload.ApiResponse;
import com.vetx.jarVes.payload.JwtAuthenticationResponse;
import com.vetx.jarVes.payload.LogInRequest;
import com.vetx.jarVes.payload.SignUpRequest;
import com.vetx.jarVes.repository.UserRepository;
import com.vetx.jarVes.security.JwtTokenProvider;
import com.vetx.jarVes.service.CreateUserAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(description = "This is the authorization controller.")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private AuthenticationManager authenticationManager;

  private UserRepository userRepository;

  private CreateUserAccountService createUserAccountService;

  private JwtTokenProvider tokenProvider;

  @Autowired
  public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                        CreateUserAccountService createUserAccountService, JwtTokenProvider tokenProvider) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.createUserAccountService = createUserAccountService;
    this.tokenProvider = tokenProvider;
  }

  @ApiOperation(value = "This endpoint will be used for User sign-in.")
  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LogInRequest loginRequest) {

    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    return ResponseEntity.ok(JwtAuthenticationResponse.builder().accessToken(tokenProvider.generateToken(authentication)).build());
  }

  @ApiOperation(value = "This endpoint will be used for User sign-up.")
  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
      HttpStatus.BAD_REQUEST);
    }

    createUserAccountService.newUser(signUpRequest.getFirstName(), signUpRequest.getLastName(),
                                     signUpRequest.getEmail(), signUpRequest.getPassword());

    return ResponseEntity.ok(new ApiResponse(true, "User registered successfully."));
  }
}