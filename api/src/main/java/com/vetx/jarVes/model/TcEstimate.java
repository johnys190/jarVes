package com.vetx.jarVes.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class TcEstimate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String voyage;

    private String account;

    private String commodity;

    private String broker;

    private String laycan;

    private Date date;

    private String reposition;

    private Integer ballastDistanceSeca;

    private Integer ballastDistanceNonSeca;

    private Integer ladenDistanceSeca;

    private Integer ladenDistanceNonSeca;

    private Double ifoPrice;

    private Double mdoPrice;

    private Double canalsCost;

    private Double miscelCosts;

    private Double lostWaitingDays;

    private Double hireRate;

    private Integer loadRate;

    private Integer dischargeRate;

    @Enumerated(EnumType.STRING)
    private LayTimeType loadLayTimeType;

    @Enumerated(EnumType.STRING)
    private LayTimeType dischargeLayTimeType;


    private Double commisionPercent;



    private Double loadPortCosts;

    private Double dischargePortCosts;

    private Double othersPortCosts;


    private Double taxesCosts;


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



}
