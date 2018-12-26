package com.vetx.jarVes.controller;

import com.vetx.jarVes.model.Vessel;
import com.vetx.jarVes.repository.VesselRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PreAuthorize("hasRole('GUEST')")
    public Vessel getVesselById(@PathVariable Long id){
        return vesselRepository.findById(id).get();
    }

    @GetMapping("/vessels")
    public List<Vessel> getAllVessel(){
        return vesselRepository.findAll();
    }
}
