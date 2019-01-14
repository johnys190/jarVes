package com.vetx.jarVes.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class VoyEstimate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long key;

    @Column(unique = true)
    private String name;

    @NonNull
    private String voyage;

    private String account;

    private String commodity;

    private String broker;

    private boolean executed;
//
//    private String laycan;
//
//    private Double quantity;
//
//    //private Double Freightrate;
//
//    private Integer loadRate;
//
//    private Integer dischargeRate;
//
//    @Enumerated(EnumType.STRING)
//    private LayTimeType loadLayTimeType;
//
//    @Enumerated(EnumType.STRING)
//    private LayTimeType dischargeLayTimeType;
//
//    private Integer ballastDistanceSeca;
//
//    private Integer ballastDistanceNonSeca;
//
//    private Integer ladenDistanceSeca;
//
//    private Integer ladenDistanceNonSeca;
//
//    private Double commisionPercent;
//
//    private String reposition;
//
//    private Date date;
//
//    private Double ifoPrice;
//
//    private Double mdoPrice;
//
//    private Double lostWaitingDays;
//
//    private Double loadPortCosts;
//
//    private Double dischargePortCosts;
//
//    private Double othersPortCosts;
//
//    private Double canalsCost;
//
//    private Double taxesCosts;
//
//    private Double miscelCosts;
//
//    private Double exinsCosts;
//
//    private Double loadTimeLostPercent;
//
//    private Double disachargeTimeLostPercent;
//
//    private Double steamingMarginTimeLostPercent;
//
//    private Double steamingDays;
//
//    private Double loadDays;
//
//    private Double dischargeDays;
//
//    private Double shexLoadDays;
//
//    private Double shexDischargeDays;
//
//    private Double totalDurationDays;
//
//    private Double grossRevenue;
//
//    private Double sailingBunkers;
//
//    private Double loadPortBunkers;
//
//    private Double dischargePortBunkers;
//
//    private Double totalBunkerCosts;
//
//    private Double expenses;
//
//    private Double commisions;
//
//    private Double taxes;
//
//    private Double exins;
//
//    private Double netRevenue;
//
//    private Double timeCharterRate;
//
//    private Double sensitivity;
}
