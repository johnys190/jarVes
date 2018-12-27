package com.vetx.jarVes.controller;

import com.vetx.jarVes.exceptions.EstimateNotFoundException;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Api(value = "This is the Voyage Estimate controller.")
@RestController
@RequestMapping("/api/voy-estimate")
public class VoyEstimateController {

  private VoyEstimateRepository voyEstimateRepository;

  @Autowired
  public VoyEstimateController(VoyEstimateRepository voyEstimateRepository) {
    this.voyEstimateRepository = voyEstimateRepository;
  }

  @ApiOperation(value = "This endpoint returns a list of Voyage Estimates.")
  @GetMapping("/voy-estimate")
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasRole('GUEST')")
  public List<VoyEstimate> getAllVoyEstimates() {
    return voyEstimateRepository.findAll();
  }

  @ApiOperation(value = "This endpoint returns a Voyage Estimate.")
  @GetMapping("/{id}")
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasRole('GUEST')")
  public VoyEstimate getEstimateById(@PathVariable Long id) {
    return voyEstimateRepository.findById(id).orElseThrow(() -> new EstimateNotFoundException(id));
  }

  @ApiOperation(value = "This endpoint updates a Voyage Estimate.")
  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasRole('ADMIN')")
  public VoyEstimate updateVoyEstimate(
      @Valid @RequestBody VoyEstimate newVoyEstimate, @PathVariable Long id) {
    newVoyEstimate.setId(id);
    return voyEstimateRepository.save(newVoyEstimate);
  }

  @ApiOperation(value = "This endpoint deletes a Voyage Estimate.")
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize("hasRole('ADMIN')")
  public void removeVoyEstimate(@PathVariable Long id) {
    voyEstimateRepository.deleteById(id);
  }

  @ApiOperation(value = "This endpoint creates a Voyage Estimate.")
  @PostMapping("/{id}")
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasRole('ADMIN')")
  VoyEstimate newVoyEstimate(@RequestBody VoyEstimate newVoyEstimate) {
    return voyEstimateRepository.save(newVoyEstimate);
  }

  @ApiOperation(value = "This endpoint exports a Voyage Estimate in PDF form.")
  @GetMapping(value = "/pdf/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
//  @PreAuthorize("hasRole('GUEST')")
  public ResponseEntity<InputStreamResource> voyEstimateReport(@PathVariable Long id) throws IOException {
    VoyEstimate voyEstimate = voyEstimateRepository.findById(id).get();

    ByteArrayInputStream bis = GeneratePDF.voyEstimatePDF(voyEstimate);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition", "inline; filename=VOYestimate.pdf");

    return ResponseEntity.ok()
        .headers(headers)
        .contentType(MediaType.APPLICATION_PDF)
        .body(new InputStreamResource(bis));
  }
}
