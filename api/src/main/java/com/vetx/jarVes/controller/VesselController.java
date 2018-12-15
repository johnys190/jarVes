package com.vetx.jarVes.controller;

import com.vetx.jarVes.model.Vessel;
import com.vetx.jarVes.repository.VesselRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Api(description = "This is the Vessel controller.")
@RestController
@RequestMapping("/api/vessel")
public class VesselController {
    private VesselRepository vesselRepository;

    @Autowired
    public VesselController(VesselRepository vesselRepository) {
        this.vesselRepository = vesselRepository;
    }

    @GetMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('GUEST')")
    public Vessel getVessel(@PathVariable Long id){
        return vesselRepository.findById(id).get();
    }
}
