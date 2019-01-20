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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
  @PreAuthorize("hasAnyRole('GUEST', 'ADMIN')")
  @ResponseStatus(HttpStatus.OK)
  public Vessel getVesselById(@PathVariable Long id) {
    return vesselRepository.findById(id).orElseThrow(() -> new VesselNotFoundException(id));
  }

  @ApiOperation(value = "This endpoint returns a list of Vessels with the Important property added.")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyRole('GUEST', 'ADMIN')")
  public List<ImportantVesselDTO> getAllVessels(@CurrentUser UserPrincipal currentUser) {
    return importantVesselsService.getImportantVessels(currentUser.getId());
  }

  @ApiOperation(value = "This endpoint deletes a Vessel by its ID.")
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize("hasRole('ADMIN')")
  public void deleteVesselById(@PathVariable Long id) {
    vesselRepository.findById(id).orElseThrow(() -> new VesselNotFoundException(id));
    vesselRepository.deleteById(id);
  }
  @ApiOperation(value = "This endpoint updates a Vessel by its ID.")
  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasRole('ADMIN')")
  public Vessel updateVessel(
          @Valid @RequestBody Vessel newVessel, @PathVariable Long id) {
    newVessel.setId(id);
    return vesselRepository.save(newVessel);
  }

  @ApiOperation(value = "This endpoint deletes an important Vessel from a User")
  @DeleteMapping("/important/{id}")
  @PreAuthorize("hasAnyRole('GUEST', 'ADMIN')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteImportantVessel(@CurrentUser UserPrincipal currentUser, @PathVariable Long id) {
    User user = userRepository.findByEmail(currentUser.getUsername()).orElseThrow(() -> new UserNotFoundException(id));
    Vessel importantVessel =
            vesselRepository.findById(id).orElseThrow(() -> new VesselNotFoundException(id));
    user.getImportantVessels().remove(importantVessel);
    userRepository.save(user);
  }

  @ApiOperation(value = "This endpoint Adds Important Vessel to a User")
  @PostMapping("/important/{id}")
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAnyRole('GUEST', 'ADMIN')")
  public void addImportantVessel(@CurrentUser UserPrincipal currentUser, @PathVariable Long id) {
    User user = userRepository.findByEmail(currentUser.getUsername()).orElseThrow(() -> new UserNotFoundException(id));
    Vessel importantVessel =
            vesselRepository.findById(id).orElseThrow(() -> new VesselNotFoundException(id));
    user.getImportantVessels().add(importantVessel);
    userRepository.save(user);
  }


  @ApiOperation(value = "This endpoint creates a Vessel.")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAnyRole('GUEST', 'ADMIN')")
  public Vessel createNewVessel(@Valid @RequestBody Vessel vessel) {
      if (vesselRepository.findByName(vessel.getName()).isPresent()){
        throw new VesselAlreadyExistsException(vessel.getName());
      }
      return vesselRepository.save(vessel);
    }
  }