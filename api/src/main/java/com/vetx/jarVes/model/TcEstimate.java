package com.vetx.jarVes.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class TcEstimate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long key;

  @Column(unique = true)
  private String name;

  @NonNull private String voyage;

  private String account;

  private String commodity;

  private String broker;

  private String laycan;

  private String reposition;

  private Date date;

  private Double hireRate;

  private Double approxDuration;

  private Double ballastBonus;

  private Double commisionPercent;

  private Integer ballastDistanceSeca;

  private Integer ballastDistanceNonSeca;

  private Integer ladenDistanceSeca;

  private Integer ladenDistanceNonSeca;

  private Double ifoPrice;

  private Double mdoPrice;

  private Double deliveryCosts;

  private Double redeliveryCosts;

  private Double canalsCost;

  private Double miscelCosts;

  private Double lostWaitingDays;

  private Double grossRevenue;

  private Double bunkerCost;

  private Double expenses;

  private Double netRevenue;

  private Integer sensitivity;

  private Double sensitivityFiveDays;

  private Integer bbGross;

  private Double totalDuration;

  private Double timeCharterRate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "Vessel_id")
  private Vessel vessel;
}
