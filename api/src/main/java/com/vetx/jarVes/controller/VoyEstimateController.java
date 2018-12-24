package com.vetx.jarVes.controller;

import com.vetx.jarVes.repository.VoyEstimateRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "This is the Voyage Estimate controller.")
@RestController
@RequestMapping("/api/VoyEstimate")
public class VoyEstimateController {

    private VoyEstimateRepository voyEstimateRepository;

    @Autowired
    public VoyEstimateController(VoyEstimateRepository voyEstimateRepository) {
        this.voyEstimateRepository = voyEstimateRepository;
    }


}
