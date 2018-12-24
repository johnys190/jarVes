package com.vetx.jarVes.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class VoyEstimate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String Voyage;

    private String Account;

    private Long Commodity;

    private String Broker;

    private Long Laycan;

    private double Quantity;

    private Long Freightrate;

    private Long Lrate;

    private Long drate;

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "Vessel_id")
    //@JsonIgnoreProperties("devices")
    //private Set<Vessel> vessel;






}
