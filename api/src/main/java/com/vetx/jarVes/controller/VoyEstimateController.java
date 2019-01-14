package com.vetx.jarVes.controller;

import com.vetx.jarVes.exceptions.EstimateNotFoundException;
import com.vetx.jarVes.exceptions.VesselNotFoundException;
import com.vetx.jarVes.exceptions.VoyEstimateAlreadyExistsException;
import com.vetx.jarVes.model.VoyEstimate;
import com.vetx.jarVes.repository.VoyEstimateRepository;
import com.vetx.jarVes.service.GeneratePDF;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Api(value = "This is the Voyage Estimate controller.")
@RestController
@RequestMapping("/api/voy-estimates")
public class VoyEstimateController {

  private VoyEstimateRepository voyEstimateRepository;

  @Autowired
  public VoyEstimateController(VoyEstimateRepository voyEstimateRepository) {
    this.voyEstimateRepository = voyEstimateRepository;
  }

  @ApiOperation(value = "This endpoint returns a list of Voyage Estimates.")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
//  @PreAuthorize("hasAnyRole('GUEST', 'ADMIN')")
  public List<VoyEstimate> getVoyEstimates() {
    return voyEstimateRepository.findAll();
  }

  @ApiOperation(value = "This endpoint returns a Voyage Estimate.")
  @GetMapping("/{key}")
  @ResponseStatus(HttpStatus.OK)
//  @PreAuthorize("hasAnyRole('GUEST', 'ADMIN')")
  public VoyEstimate getVoyEstimate(@PathVariable Long key) {
    return voyEstimateRepository.findById(key).orElseThrow(() -> new EstimateNotFoundException(key));
  }

  @ApiOperation(value = "This endpoint updates a Voyage Estimate.")
  @PutMapping("/{key}")
  @ResponseStatus(HttpStatus.OK)
//  @PreAuthorize("hasRole('ADMIN')")
  public VoyEstimate updateVoyEstimate(@Valid @RequestBody VoyEstimate newVoyEstimate, @PathVariable Long key) {
    newVoyEstimate.setKey(key);
    return voyEstimateRepository.save(newVoyEstimate);
  }

  @ApiOperation(value = "This endpoint deletes a Voyage Estimate.")
  @DeleteMapping("/{key}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
//  @PreAuthorize("hasRole('ADMIN')")
  public void deleteVoyEstimate(@PathVariable Long key) {
    voyEstimateRepository.findById(key).orElseThrow(() -> new VesselNotFoundException(key));
    voyEstimateRepository.deleteById(key);
  }

  @ApiOperation(value = "This endpoint creates a Voyage Estimate.")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
//  @PreAuthorize("hasRole('ADMIN')")
  VoyEstimate newVoyEstimate(@Valid @RequestBody VoyEstimate newVoyEstimate) {
    if (voyEstimateRepository.findByName(newVoyEstimate.getName()).isPresent()) {
      throw new VoyEstimateAlreadyExistsException(newVoyEstimate.getName());
    }
    return voyEstimateRepository.save(newVoyEstimate);
  }

  @ApiOperation(value = "This endpoint exports a Voyage Estimate in PDF form.")
  @GetMapping(value = "/pdf/{key}", produces = MediaType.APPLICATION_PDF_VALUE)
//  @PreAuthorize("hasAnyRole('GUEST', 'ADMIN')")
  public ResponseEntity<InputStreamResource> voyEstimateReport(@PathVariable Long key) throws IOException {
    VoyEstimate voyEstimate = voyEstimateRepository.findById(key).get();
    ByteArrayInputStream bis = GeneratePDF.voyEstimatePDF(voyEstimate);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition", "inline; filename=VOYestimate.pdf");

    return ResponseEntity.ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(bis));
  }
}