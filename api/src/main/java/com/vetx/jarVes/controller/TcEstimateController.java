package com.vetx.jarVes.controller;


import com.vetx.jarVes.model.TcEstimate;
import com.vetx.jarVes.repository.TcEstimateRepository;
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
    public TcEstimate getEstimateById(@PathVariable Long id){
        return tcEstimateRepository.findById(id).get();
    }

//    @GetMapping(value = "/tcestimate-pdf", produces = MediaType.APPLICATION_PDF_VALUE)
//    public ResponseEntity<InputStreamResource> customersReport() throws IOException {
//        List<TcEstimate> tcEstimates = (List<TcEstimate>) tcEstimateRepository.findAll();
//
//        ByteArrayInputStream bis = GeneratePDF.voyEstimatePDF(tcEstimates);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Disposition", "inline; filename=VOYestimate.pdf");
//
//        return ResponseEntity
//                .ok()
//                .headers(headers)
//                .contentType(MediaType.APPLICATION_PDF)
//                .body(new InputStreamResource(bis));
//    }
}
