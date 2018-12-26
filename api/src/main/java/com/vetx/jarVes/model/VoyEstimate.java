package com.vetx.jarVes.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class VoyEstimate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String voyage;

    private String account;

    private String commodity;

    private String broker;

    private String laycan;

    private Double quantity;

    //private Double Freightrate;

    private Integer loadRate;

    private Integer dischargeRate;

    @Enumerated(EnumType.STRING)
    private LayTimeType loadLayTimeType;

    @Enumerated(EnumType.STRING)
    private LayTimeType dischargeLayTimeType;

    private Integer ballastDistanceSeca;

    private Integer ballastDistanceNonSeca;

    private Integer ladenDistanceSeca;

    private Integer ladenDistanceNonSeca;

    private Double commisionPercent;

    private String reposition;

    private Date date;

    private Double ifoPrice;

    private Double mdoPrice;

    private Double lostWaitingDays;

    private Double loadPortCosts;

    private Double dischargePortCosts;

    private Double othersPortCosts;

    private Double canalsCost;

    private Double taxesCosts;

    private Double miscelCosts;

    private Double exinsCosts;

    private Double loadTimeLostPercent;

    private Double disachargeTimeLostPercent;

    private Double steamingMarginTimeLostPercent;

    private Double steamingDays;

    private Double loadDays;

    private Double dischargeDays;

    private Double shexLoadDays;

    private Double shexDischargeDays;

    private Double totalDurationDays;

    private Double grossRevenueResult;

    private Double sailingBunkersResult;

    private Double loadPortBunkersResult;

    private Double dischargePortBunkersResult;

    private Double totalBunkerCostsResult;

    private Double expensesResult;

    private Double commisionsResult;

    private Double taxesResult;

    private Double exinsResult;

    private Double netRevenueResult;

    private Double timeCharterRateResult;

    private Double sensitivity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Vessel_id")
    private Vessel vessel;






}
