package com.vetx.jarVes.controller;

import com.vetx.jarVes.exceptions.EstimateNotFoundException;
import com.vetx.jarVes.model.TcEstimate;
import com.vetx.jarVes.repository.TcEstimateRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "This is the Time Charter Estimate controller.")
@RestController
@RequestMapping("/api/tc-estimate")
public class TcEstimateController {
  private TcEstimateRepository tcEstimateRepository;

  @Autowired
  public TcEstimateController(TcEstimateRepository tcEstimateRepository) {
    this.tcEstimateRepository = tcEstimateRepository;
  }

  @ApiOperation(value = "This endpoint returns a list of all Time Charter Estimates.")
  @GetMapping("/tc-estimates")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyRole('GUEST', 'ADMIN')")
  public List<TcEstimate> getAllTcEstimates() {
    return tcEstimateRepository.findAll();
  }

  @ApiOperation(value = "This endpoint returns a Time Charter Estimate by its ID.")
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyRole('GUEST', 'ADMIN')")
  public TcEstimate getTcEstimateById(@PathVariable Long id) {
    return tcEstimateRepository.findById(id).orElseThrow(() -> new EstimateNotFoundException(id));
  }

  @ApiOperation(value = "This endpoint updates a Time Charter Estimate by its ID.")
  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasRole('ADMIN')")
  public TcEstimate updateTcEstimate(
      @Valid @RequestBody TcEstimate newTcEstimate, @PathVariable Long id) {
    newTcEstimate.setId(id);
    return tcEstimateRepository.save(newTcEstimate);
  }

  @ApiOperation(value = "This endpoint deletes a Time Charter Estimate by its ID.")
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize("hasRole('ADMIN')")
  public void deleteTcEstimateById(@PathVariable Long id) {
    tcEstimateRepository.delete(tcEstimateRepository.findById(id).get());
  }

  @ApiOperation(value = "This endpoint creates a Time Charter Estimate.")
  @PostMapping("/create-tc-estimate")
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasRole('ADMIN')")
  public TcEstimate createTcEstimate(@Valid @RequestBody TcEstimate newTcEstimate) {
    return tcEstimateRepository.save(newTcEstimate);
  }
}
