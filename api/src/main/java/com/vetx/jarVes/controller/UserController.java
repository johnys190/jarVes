package com.vetx.jarVes.controller;

import com.vetx.jarVes.exceptions.CannotDeleteUserException;
import com.vetx.jarVes.exceptions.UserAlreadyExistsException;
import com.vetx.jarVes.exceptions.UserNotFoundException;
import com.vetx.jarVes.model.Role;
import com.vetx.jarVes.model.RoleName;
import com.vetx.jarVes.model.User;
import com.vetx.jarVes.payload.ApiResponse;
import com.vetx.jarVes.payload.UserSummary;
import com.vetx.jarVes.repository.UserRepository;
import com.vetx.jarVes.security.CurrentUser;
import com.vetx.jarVes.security.UserPrincipal;
import com.vetx.jarVes.service.CreateUserAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
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
//    @PreAuthorize("hasRole('GUEST')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        return new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
    }

    @ApiOperation(value = "This endpoint will return a list of all Users.")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
//    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @ApiOperation(value = "This endpoint will return a User.")
    @GetMapping("/{key}")
    @ResponseStatus(HttpStatus.OK)
//    @PreAuthorize("hasRole('ADMIN')")
    public User getUser(@PathVariable Long key) {
        return userRepository.findById(key).orElseThrow(() -> new UserNotFoundException(key));
    }

    @ApiOperation(value = "This endpoint will create a User.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createNewUser(@Valid @RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException(user.getEmail());
        }
        createUserAccountService.newUser(user.getName(), user.getLastName(), user.getEmail(), user.getPassword());
        return ResponseEntity.ok(new ApiResponse(true, "Guest user created successfully."));
    }

    @ApiOperation(value = "This endpoint will update a User.")
    @PutMapping("/{key}")
    @ResponseStatus(HttpStatus.OK)
//    @PreAuthorize("hasRole('ADMIN')")
    public User updateUser(@PathVariable Long key, @Valid @RequestBody User updatedUser) {
        updatedUser.setKey(key);
        return userRepository.save(updatedUser);
    }

    @ApiOperation(value = "This endpoint will delete a User.")
    @DeleteMapping("/{key}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable Long key, @CurrentUser UserPrincipal currentUser) {
        User user = userRepository.findById(key).orElseThrow(() -> new UserNotFoundException(key));
        if ((user.getEmail().equals(currentUser.getUsername())) ||
                (user.getRoles().stream().filter(role -> role.getName().equals(RoleName.ROLE_ADMIN)).count() != 0))
        {
            throw new CannotDeleteUserException();
        }
        userRepository.deleteById(key);
    }
}