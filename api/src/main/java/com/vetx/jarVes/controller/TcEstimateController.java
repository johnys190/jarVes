package com.vetx.jarVes.controller;

import com.google.common.net.HttpHeaders;
import com.vetx.jarVes.exceptions.EstimateNotFoundException;
import com.vetx.jarVes.exceptions.TcEstimateAlreadyExistsException;
import com.vetx.jarVes.model.TcEstimate;
import com.vetx.jarVes.repository.TcEstimateRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Api(value = "This is the Time Charter Estimate controller.")
@RestController
@RequestMapping("/api/tc-estimates")
public class TcEstimateController {
  private TcEstimateRepository tcEstimateRepository;

  @Autowired
  public TcEstimateController(TcEstimateRepository tcEstimateRepository) {
    this.tcEstimateRepository = tcEstimateRepository;
  }

  @ApiOperation(value = "This endpoint returns a list of all Time Charter Estimates.")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
//  @PreAuthorize("hasAnyRole('GUEST', 'ADMIN')")
  public List<TcEstimate> getAllTcEstimates() {
    return tcEstimateRepository.findAll();
  }

  @ApiOperation(value = "This endpoint returns a Time Charter Estimate by its KEY.")
  @GetMapping("/{key}")
  @ResponseStatus(HttpStatus.OK)
//  @PreAuthorize("hasAnyRole('GUEST', 'ADMIN')")
  public TcEstimate getTcEstimateById(@PathVariable Long key) {
    return tcEstimateRepository.findById(key).orElseThrow(() -> new EstimateNotFoundException(key));
  }

  @ApiOperation(value = "This endpoint updates a Time Charter Estimate by its KEY.")
  @PutMapping("/{key}")
  @ResponseStatus(HttpStatus.OK)
//  @PreAuthorize("hasRole('ADMIN')")
  public TcEstimate updateTcEstimate(@Valid @RequestBody TcEstimate newTcEstimate, @PathVariable Long key) {
    TcEstimate tcEstimate = tcEstimateRepository.findById(key).orElseThrow(() -> new EstimateNotFoundException(key));
    tcEstimate.setName(newTcEstimate.getName());
    tcEstimate.setVoyage(newTcEstimate.getVoyage());
    tcEstimate.setExecuted(newTcEstimate.isExecuted());
    tcEstimate.setAccount(newTcEstimate.getAccount());
    tcEstimate.setCommodity(newTcEstimate.getCommodity());
    tcEstimate.setBroker(newTcEstimate.getBroker());
    tcEstimate.setLaycan(newTcEstimate.getLaycan());
    tcEstimate.setReposition(newTcEstimate.getReposition());
    tcEstimate.setDate(newTcEstimate.getDate());
    tcEstimate.setHireRate(newTcEstimate.getHireRate());
    tcEstimate.setApproxDuration(newTcEstimate.getApproxDuration());
    tcEstimate.setBallastBonus(newTcEstimate.getBallastBonus());
    tcEstimate.setCommisionPercent(newTcEstimate.getCommisionPercent());
    tcEstimate.setBallastDistanceNonSeca(newTcEstimate.getBallastDistanceNonSeca());
    tcEstimate.setBallastDistanceSeca(newTcEstimate.getBallastDistanceSeca());
    tcEstimate.setLadenDistanceSeca(newTcEstimate.getLadenDistanceSeca());
    tcEstimate.setLadenDistanceNonSeca(newTcEstimate.getLadenDistanceNonSeca());
    tcEstimate.setIfoPrice(newTcEstimate.getIfoPrice());
    tcEstimate.setMdoPrice(newTcEstimate.getMdoPrice());
    tcEstimate.setDeliveryCosts(newTcEstimate.getDeliveryCosts());
    tcEstimate.setRedeliveryCosts(newTcEstimate.getRedeliveryCosts());
    tcEstimate.setCanalsCost(newTcEstimate.getCanalsCost());
    tcEstimate.setMiscelCosts(newTcEstimate.getMiscelCosts());
    tcEstimate.setLostWaitingDays(newTcEstimate.getLostWaitingDays());
    tcEstimate.setGrossRevenue(newTcEstimate.getGrossRevenue());
    tcEstimate.setBunkerCost(newTcEstimate.getBunkerCost());
    tcEstimate.setExpenses(newTcEstimate.getExpenses());
    tcEstimate.setNetRevenue(newTcEstimate.getNetRevenue());
    tcEstimate.setSensitivity(newTcEstimate.getSensitivity());
    tcEstimate.setSensitivityFiveDays(newTcEstimate.getSensitivityFiveDays());
    tcEstimate.setBbGross(newTcEstimate.getBbGross());
    tcEstimate.setTotalDuration(newTcEstimate.getTotalDuration());
    tcEstimate.setTimeCharterRate(newTcEstimate.getTimeCharterRate());
    tcEstimate.setVessel(newTcEstimate.getVessel());
    return tcEstimateRepository.save(tcEstimate);
  }

  @ApiOperation(value = "This endpoint deletes a Time Charter Estimate by its ID.")
  @DeleteMapping("/{key}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
//  @PreAuthorize("hasRole('ADMIN')")
  public void deleteTcEstimateById(@PathVariable Long key) {
    tcEstimateRepository.findById(key).orElseThrow(() -> new EstimateNotFoundException(key));
    tcEstimateRepository.deleteById(key);
  }

  @ApiOperation(value = "This endpoint creates a Time Charter Estimate.")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
//  @PreAuthorize("hasRole('ADMIN')")
  public TcEstimate createTcEstimate(@Valid @RequestBody TcEstimate newTcEstimate) {
    if (tcEstimateRepository.findByName(newTcEstimate.getName()).isPresent()) {
      throw new TcEstimateAlreadyExistsException(newTcEstimate.getName());
    }
    return tcEstimateRepository.save(newTcEstimate);
  }
  @ApiOperation(value = "This endpoint produces a Time Charter Estimate in plaintext format.")
  @GetMapping("/txt/{key}")
  @ResponseStatus(HttpStatus.CREATED)
//  @PreAuthorize("hasRole('ADMIN')")
  public String produceTxt(@PathVariable Long key, HttpServletResponse response) {
    if (!(tcEstimateRepository.findById(key).isPresent())) {
      throw new EstimateNotFoundException(key);
    }
    response.setContentType("text/plain");
    response.setCharacterEncoding("UTF-8");
//  response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "TimeCharter.txt");
    return tcEstimateRepository.findById(key).get().toText();
  }
}