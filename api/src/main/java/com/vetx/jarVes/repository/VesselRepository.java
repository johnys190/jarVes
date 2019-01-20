package com.vetx.jarVes.repository;

import com.vetx.jarVes.model.Vessel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@Repository
@CrossOrigin
public interface VesselRepository extends JpaRepository<Vessel, Long> {
    Optional<Vessel> findByName(String name);
}
