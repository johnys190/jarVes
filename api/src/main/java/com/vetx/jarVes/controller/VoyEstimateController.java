package com.vetx.jarVes.controller;

import com.vetx.jarVes.model.VoyEstimate;
import com.vetx.jarVes.repository.VoyEstimateRepository;
import com.vetx.jarVes.service.GeneratePDF;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Api(description = "This is the Voyage Estimate controller.")
@RestController
@RequestMapping("/api/VoyEstimate")
public class VoyEstimateController {

    private VoyEstimateRepository voyEstimateRepository;

    @Autowired
    public VoyEstimateController(VoyEstimateRepository voyEstimateRepository) {
        this.voyEstimateRepository = voyEstimateRepository;
    }

    @GetMapping("/voyestimates")
    @ResponseBody
    public List<VoyEstimate> getallVoyEstimates(){
        return voyEstimateRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    //@PreAuthorize("hasRole('GUEST')")
    public VoyEstimate getEstimateById(@PathVariable Long id){
        return voyEstimateRepository.findById(id).get();
    }

    @GetMapping(value = "/voyestimate_pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> customersReport() throws IOException {
        List<VoyEstimate> voyEstimates = (List<VoyEstimate>) voyEstimateRepository.findAll();

        ByteArrayInputStream bis = GeneratePDF.voyEstimatePDF(voyEstimates);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=VOYestimate.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
