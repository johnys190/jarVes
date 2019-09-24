package com.vetx.jarVes.controller;

import com.vetx.jarVes.exceptions.CannotDeleteUserException;
import com.vetx.jarVes.exceptions.UserAlreadyExistsException;
import com.vetx.jarVes.exceptions.UserNotFoundException;
import com.vetx.jarVes.model.RoleName;
import com.vetx.jarVes.model.User;
import com.vetx.jarVes.payload.ApiResponse;
import com.vetx.jarVes.payload.SignUpRequest;
import com.vetx.jarVes.payload.UserSummary;
import com.vetx.jarVes.repository.UserRepository;
import com.vetx.jarVes.security.CurrentUser;
import com.vetx.jarVes.security.UserPrincipal;
import com.vetx.jarVes.service.CreateUserAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "This is the User controller.")
@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserRepository userRepository;
    private CreateUserAccountService createUserAccountService;

    public UserController(UserRepository userRepository, CreateUserAccountService createUserAccountService) {
        this.userRepository = userRepository;
        this.createUserAccountService = createUserAccountService;
    }

    @ApiOperation(value = "This endpoint will return the current User.")
    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('GUEST', 'ADMIN')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        User user = userRepository.getOne(currentUser.getId());
        return UserSummary.builder()
            .id(user.getId())
            .email(user.getEmail())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .build();
    }

    @ApiOperation(value = "This endpoint will return a list of all Users.")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @ApiOperation(value = "This endpoint will return a User.")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public User getUser(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @ApiOperation(value = "This endpoint will create a User.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public User createNewUser(@Valid @RequestBody SignUpRequest user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException(user.getEmail());
        }
        return createUserAccountService.newUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
    }

    @ApiOperation(value = "This endpoint will update a User.")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public User updateUser(@PathVariable Long id, @Valid @RequestBody User updatedUser) {
        updatedUser.setId(id);
        return userRepository.save(updatedUser);
    }

    @ApiOperation(value = "This endpoint will delete a User.")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable Long id, @CurrentUser UserPrincipal currentUser) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        if ((user.getEmail().equals(currentUser.getUsername())) ||
                (user.getRoles().stream().filter(role -> role.getName().equals(RoleName.ROLE_ADMIN)).count() != 0))
        {
            throw new CannotDeleteUserException();
        }
        userRepository.deleteById(id);
    }
}