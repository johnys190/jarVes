package com.vetx.jarVes.controller;

import com.vetx.jarVes.exceptions.UserNotFoundException;
import com.vetx.jarVes.exceptions.VesselNotFoundException;
import com.vetx.jarVes.model.Vessel;
import com.vetx.jarVes.payload.UserSummary;
import com.vetx.jarVes.model.User;
import com.vetx.jarVes.model.Vessel;
import com.vetx.jarVes.payload.UserSummary;
import com.vetx.jarVes.repository.UserRepository;
import com.vetx.jarVes.repository.VesselRepository;
import com.vetx.jarVes.security.CurrentUser;
import com.vetx.jarVes.security.UserPrincipal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@Api(value = "This is the User controller.")
@RestController
@RequestMapping("/api")
public class UserController {
    private UserRepository userRepository;

    private VesselRepository vesselRepository;

    public UserController(UserRepository userRepository, VesselRepository vesselRepository) {
        this.userRepository = userRepository;
        this.vesselRepository = vesselRepository;
    }

    @ApiOperation(value = "This endpoint will return the current User.")
    @GetMapping("/users/me")
    @PreAuthorize("hasRole('GUEST')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
        return userSummary;
    }

    @ApiOperation(value = "This endpoint will return a list of all Users.")
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    @ApiOperation(value = "This endpoint will add an ImportantVessel to a User.")
    @PostMapping("/add-important-vessel/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('GUEST')")
    public void addImportantVessel(@CurrentUser UserPrincipal currentUser, @PathVariable Long id){
        User user = userRepository.findById(currentUser.getId()).orElseThrow(() -> new UserNotFoundException(currentUser.getId()));
        Vessel importantVessel = vesselRepository.findById(id).orElseThrow(() -> new VesselNotFoundException(id));
        user.getImportantVessels().add(importantVessel);
        userRepository.save(user);
    }

    @ApiOperation(value = "This endpoint will return a list of all Users.")
    @PostMapping("/remove-important-vessel/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('GUEST')")
    public void deleteImportantVessel(@CurrentUser UserPrincipal currentUser, @PathVariable Long id){
        User user = userRepository.findById(currentUser.getId()).orElseThrow(() -> new UserNotFoundException(currentUser.getId()));
        Vessel importantVessel = vesselRepository.findById(id).orElseThrow(() -> new VesselNotFoundException(id));
        user.getImportantVessels().remove(importantVessel);
        userRepository.save(user);
    }



//    @ApiOperation(value = "This endpoint will create a User.")
//    @PostMapping("/create_user")
//    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasRole('ADMIN')")
//    public User createNewUser(@Valid @RequestBody User user){
//        try {
//            return userRepository.save(user);
//        } catch (UsernameNotFoundException ex) {
//            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Cannot create User", ex);
//        }
//    }
}
