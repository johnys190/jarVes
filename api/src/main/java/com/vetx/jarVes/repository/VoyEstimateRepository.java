package com.vetx.jarVes.repository;

import com.vetx.jarVes.model.VoyEstimate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@Repository
@CrossOrigin
public interface VoyEstimateRepository extends JpaRepository<VoyEstimate, Long> {
    Optional<VoyEstimate> findByName(String name);
}
