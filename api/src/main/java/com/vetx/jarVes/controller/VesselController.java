package com.vetx.jarVes.controller;

import com.vetx.jarVes.exceptions.UserNotFoundException;
import com.vetx.jarVes.exceptions.VesselAlreadyExistsException;
import com.vetx.jarVes.exceptions.VesselNotFoundException;
import com.vetx.jarVes.model.User;
import com.vetx.jarVes.model.Vessel;
import com.vetx.jarVes.payload.ImportantVesselDTO;
import com.vetx.jarVes.repository.UserRepository;
import com.vetx.jarVes.repository.VesselRepository;
import com.vetx.jarVes.security.CurrentUser;
import com.vetx.jarVes.security.UserPrincipal;
import com.vetx.jarVes.service.ImportantVesselsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

@Api(value = "This is the Vessel controller.")
@RestController
@RequestMapping("/api/vessels")
public class VesselController {
  private VesselRepository vesselRepository;

  private ImportantVesselsService importantVesselsService;

  private UserRepository userRepository;

  @Autowired
  public VesselController(VesselRepository vesselRepository, ImportantVesselsService importantVesselsService, UserRepository userRepository) {
    this.vesselRepository = vesselRepository;
    this.importantVesselsService = importantVesselsService;
    this.userRepository = userRepository;
  }


  @ApiOperation(value = "This endpoint returns a Vessel by its ID.")
  @GetMapping("/{id}")
//  @PreAuthorize("hasAnyRole('GUEST', 'ADMIN')")
  @ResponseStatus(HttpStatus.OK)
  public Vessel getVesselById(@PathVariable Long id) {
    return vesselRepository.findById(id).orElseThrow(() -> new VesselNotFoundException(id));
  }

  @ApiOperation(value = "This endpoint returns a list of Vessels with the Important property added.")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
//  @PreAuthorize("hasRole('GUEST')")
  public List<ImportantVesselDTO> getAllVessel(@CurrentUser UserPrincipal currentUser) {
    return importantVesselsService.getImportantVessels(currentUser.getId());
  }

  @ApiOperation(value = "This endpoint deletes a Vessel by its ID.")
  @DeleteMapping("/{key}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
//  @PreAuthorize("hasRole('ADMIN')")
  public void deleteVesselById(@PathVariable Long key) {
    vesselRepository.findById(key).orElseThrow(() -> new VesselNotFoundException(key));
    vesselRepository.deleteById(key);
  }
  @ApiOperation(value = "This endpoint updates a Vessel by its ID.")
  @PutMapping("/{key}")
  @ResponseStatus(HttpStatus.OK)
//  @PreAuthorize("hasRole('ADMIN')")
  public Vessel updateVessel(
          @Valid @RequestBody Vessel newVessel, @PathVariable Long key) {
    newVessel.setKey(key);
    return vesselRepository.save(newVessel);
  }

  @ApiOperation(value = "This endpoint deletes an important Vessel from a User")
  @DeleteMapping("/important/{key}")
//  @PreAuthorize("hasAnyRole('GUEST', 'ADMIN')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteImportantVessel(@CurrentUser UserPrincipal currentUser, @PathVariable Long key) {
    User user = userRepository.findByEmail(currentUser.getUsername()).orElseThrow(() -> new UserNotFoundException(key));
    Vessel importantVessel =
            vesselRepository.findById(key).orElseThrow(() -> new VesselNotFoundException(key));
    user.getImportantVessels().remove(importantVessel);
    userRepository.save(user);
  }

  @ApiOperation(value = "This endpoint Adds Important Vessel to a User")
  @PostMapping("/important/{key}")
  @ResponseStatus(HttpStatus.CREATED)
//  @PreAuthorize("hasAnyRole('GUEST', 'ADMIN')")
  public void addImportantVessel(@CurrentUser UserPrincipal currentUser, @PathVariable Long key) {
    User user = userRepository.findByEmail(currentUser.getUsername()).orElseThrow(() -> new UserNotFoundException(key));
    Vessel importantVessel =
            vesselRepository.findById(key).orElseThrow(() -> new VesselNotFoundException(key));
    user.getImportantVessels().add(importantVessel);
    userRepository.save(user);
  }


  @ApiOperation(value = "This endpoint creates a Vessel.")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
//  @PreAuthorize("hasRole('ADMIN')")
  public Vessel createNewVessel(@Valid @RequestBody Vessel vessel) {
      if (vesselRepository.findById(vessel.getKey()).isPresent()){
        throw new VesselAlreadyExistsException(vessel.getKey());
      }
      return vesselRepository.save(vessel);
    }
  }