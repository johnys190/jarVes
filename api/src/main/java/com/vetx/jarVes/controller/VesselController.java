package com.vetx.jarVes.controller;

import com.vetx.jarVes.exceptions.VesselNotFoundException;
import com.vetx.jarVes.model.Vessel;
import com.vetx.jarVes.repository.VesselRepository;
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
        return vesselRepository.findById(id).orElseThrow(() -> new VesselNotFoundException(id));
    }

    @GetMapping("/vessels")
    public List<Vessel> getAllVessel(){
        return vesselRepository.findAll();
    }

    @ApiOperation(value = "This endpoint will delete a Vessel.")
    @DeleteMapping("/delete-vessel/{vesselId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteVesselById(@PathVariable Long vesselId) {
        try {
            vesselRepository.deleteById(vesselId);
        } catch (UsernameNotFoundException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Cannot delete Vessel", ex);
        }
    }

    @ApiOperation(value = "This endpoint will create a Vessel.")
    @PostMapping("/create-vessel")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public Vessel createNewVessel(@Valid @RequestBody Vessel vessel){
        try {
            return vesselRepository.save(vessel);
        } catch (UsernameNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Cannot create Vessel", ex);
        }
    }

}
