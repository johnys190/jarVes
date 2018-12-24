package com.vetx.jarVes.repository;

import com.vetx.jarVes.model.VoyEstimate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin
public interface VoyEstimateRepository extends JpaRepository<VoyEstimate,Long> {

}

