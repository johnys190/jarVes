package com.vetx.jarVes.controller;


import com.vetx.jarVes.exceptions.EstimateNotFoundException;
import com.vetx.jarVes.exceptions.VesselNotFoundException;
import com.vetx.jarVes.model.TcEstimate;
import com.vetx.jarVes.repository.TcEstimateRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@Api(description = "This is the Time Charter Estimate controller.")
@RestController
@RequestMapping("/api/tc-estimate")
public class TcEstimateController {
    private TcEstimateRepository tcEstimateRepository;

    @Autowired
    public TcEstimateController(TcEstimateRepository tcEstimateRepository) {
        this.tcEstimateRepository = tcEstimateRepository;
    }

    @GetMapping("/tc-estimates")
    public List<TcEstimate> getallTcEstimates(){
        return tcEstimateRepository.findAll();
    }

    @GetMapping("/{id}")
    //@PreAuthorize("hasRole('GUEST')")
    public TcEstimate getTcEstimateById(@PathVariable Long id){
        return tcEstimateRepository.findById(id).orElseThrow(() -> new EstimateNotFoundException(id));
    }

    @PutMapping("/{id}")
    public TcEstimate updateVoyEstimate (@Valid @RequestBody TcEstimate newTcEstimate,@PathVariable Long id) {
        newTcEstimate.setId(id);
        return tcEstimateRepository.save(newTcEstimate);
    }

    @DeleteMapping("/{id}")
    public void deleteTcEstimateById(@PathVariable Long id){
        tcEstimateRepository.delete(tcEstimateRepository.findById(id).get());
    }

    @PostMapping("/create-tc-estimate")
    public TcEstimate createTcEstimate(@Valid @RequestBody TcEstimate newTcEstimate){
        return tcEstimateRepository.save(newTcEstimate);
    }
}
