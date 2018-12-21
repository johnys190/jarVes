package com.vetx.jarVes.controller;

import com.vetx.jarVes.payload.UserSummary;
import com.vetx.jarVes.model.User;
import com.vetx.jarVes.repository.UserRepository;
import com.vetx.jarVes.security.CurrentUser;
import com.vetx.jarVes.security.UserPrincipal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Api(description = "This is the User controller.")
@RestController
@RequestMapping("api/user")
public class UserController {
    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @ApiOperation(value = "This endpoint will return the current User.")
    @GetMapping("/me")
    @PreAuthorize("hasRole('GUEST')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
        return userSummary;
    }

    @ApiOperation(value = "This endpoint will return a list of all Users.")
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('GUEST')")
    public List<User> getUsers(){
        try {
            return userRepository.findAll();
        } catch (UsernameNotFoundException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Cannot display Users", ex);
        }
    }
}
