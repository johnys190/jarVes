package com.vetx.jarVes.repository;

import com.vetx.jarVes.model.TcEstimate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin
public interface TcEstimateRepository extends JpaRepository<TcEstimate, Long> {}
