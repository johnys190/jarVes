package com.vetx.jarVes.controller;

import com.vetx.jarVes.model.Vessel;
import com.vetx.jarVes.payload.UserSummary;
import com.vetx.jarVes.model.User;
import com.vetx.jarVes.repository.UserRepository;
import com.vetx.jarVes.repository.VesselRepository;
import com.vetx.jarVes.security.CurrentUser;
import com.vetx.jarVes.security.UserPrincipal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@Api(description = "This is the User controller.")
@RestController
@RequestMapping("api/user")
public class UserController {
    private UserRepository userRepository;

    private VesselRepository vesselRepository;

    public UserController(UserRepository userRepository, VesselRepository vesselRepository) {
        this.userRepository = userRepository;
        this.vesselRepository = vesselRepository;
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

    @ApiOperation(value = "This endpoint will create a Vessel.")
    @PostMapping("/create-vessel")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public Vessel createNewVessel(@Valid @RequestBody Vessel vessel){
        try {
            return vesselRepository.save(vessel);
        } catch (UsernameNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Cannot create Vessel", ex);
        }
    }

    @ApiOperation(value = "This endpoint will delete a Vessel.")
    @DeleteMapping("/delete-vessel/{vesselId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteVesselById(@PathVariable Long vesselId) {
        try {
            vesselRepository.deleteById(vesselId);
        } catch (UsernameNotFoundException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Cannot delete Vessel", ex);
        }
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
