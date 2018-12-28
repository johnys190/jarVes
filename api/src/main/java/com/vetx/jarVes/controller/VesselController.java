package com.vetx.jarVes.controller;

import com.vetx.jarVes.exceptions.VesselNotFoundException;
import com.vetx.jarVes.model.Vessel;
import com.vetx.jarVes.payload.ImportantVesselDTO;
import com.vetx.jarVes.repository.VesselRepository;
import com.vetx.jarVes.security.CurrentUser;
import com.vetx.jarVes.security.UserPrincipal;
import com.vetx.jarVes.service.ImportantVesselsService;
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

@Api(value = "This is the Vessel controller.")
@RestController
@RequestMapping("/api/vessel")
public class VesselController {
  private VesselRepository vesselRepository;

  private ImportantVesselsService importantVesselsService;

  @Autowired
  public VesselController(
      VesselRepository vesselRepository, ImportantVesselsService importantVesselsService) {
    this.vesselRepository = vesselRepository;
    this.importantVesselsService = importantVesselsService;
  }

  @ApiOperation(value = "This endpoint returns a Vessel by its ID.")
  @GetMapping("/{id}")
  @PreAuthorize("hasAnyRole('GUEST', 'ADMIN')")
  @ResponseStatus(HttpStatus.OK)
  public Vessel getVesselById(@PathVariable Long id) {
    return vesselRepository.findById(id).orElseThrow(() -> new VesselNotFoundException(id));
  }

  @ApiOperation(value = "This endpoint updates a Vessel by its ID.")
  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyRole('GUEST', 'ADMIN')")
  public Vessel updateVessel(
          @Valid @RequestBody Vessel vessel, @PathVariable Long id) {
    vessel.setId(id);
    return vesselRepository.save(vessel);
  }

  @ApiOperation(value = "This endpoint returns a list of all Time Charter Estimates.")
  @GetMapping("/all")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyRole('GUEST', 'ADMIN')")
  public List<Vessel> getAllVessels() {
    return vesselRepository.findAll();
  }

  @ApiOperation(value = "This endpoint deletes a Vessel by its ID.")
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyRole('GUEST', 'ADMIN')")
  public void deleteVesselById(@PathVariable Long vesselId) {
    try {
      vesselRepository.deleteById(vesselId);
    } catch (UsernameNotFoundException ex) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot delete Vessel", ex);
    }
  }

  @ApiOperation(value = "This endpoint creates a Vessel.")
  @PostMapping("/new")
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAnyRole('GUEST', 'ADMIN')")
  public Vessel createNewVessel(@Valid @RequestBody Vessel vessel) {
    try {
      return vesselRepository.save(vessel);
    } catch (UsernameNotFoundException ex) {
      throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Cannot create Vessel", ex);
    }
  }
}
