package com.vetx.jarVes.model;

import com.vetx.jarVes.model.audit.UserDateAudit;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class TcEstimate extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long key;

  @Column(unique = true)
  private String name;

  @NonNull private String voyage;

  private boolean executed;

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

  public String toText() {
    return "TcEstimate{" +
            "name='" + name + '\n' +
            ", voyage='" + voyage + '\n' +
            ", executed=" + executed +
            ", account='" + account + '\n' +
            ", commodity='" + commodity + '\n' +
            ", broker='" + broker + '\n' +
            ", laycan='" + laycan + '\n' +
            ", reposition='" + reposition + '\n' +
            ", date=" + date +
            ", hireRate=" + hireRate +
            ", approxDuration=" + approxDuration +
            ", ballastBonus=" + ballastBonus +
            ", commisionPercent=" + commisionPercent +
            ", ballastDistanceSeca=" + ballastDistanceSeca +
            ", ballastDistanceNonSeca=" + ballastDistanceNonSeca +
            ", ladenDistanceSeca=" + ladenDistanceSeca +
            ", ladenDistanceNonSeca=" + ladenDistanceNonSeca +
            ", ifoPrice=" + ifoPrice +
            ", mdoPrice=" + mdoPrice +
            ", deliveryCosts=" + deliveryCosts +
            ", redeliveryCosts=" + redeliveryCosts +
            ", canalsCost=" + canalsCost +
            ", miscelCosts=" + miscelCosts +
            ", lostWaitingDays=" + lostWaitingDays +
            ", grossRevenue=" + grossRevenue +
            ", bunkerCost=" + bunkerCost +
            ", expenses=" + expenses +
            ", netRevenue=" + netRevenue +
            ", sensitivity=" + sensitivity +
            ", sensitivityFiveDays=" + sensitivityFiveDays +
            ", bbGross=" + bbGross +
            ", totalDuration=" + totalDuration +
            ", timeCharterRate=" + timeCharterRate +
            ", vessel=" + vessel +
            '}';
  }

  public TcEstimate copyTcEstimate(TcEstimate newTcEstimate) {
    this.setName(newTcEstimate.getName());
    this.setVoyage(newTcEstimate.getVoyage());
    this.setExecuted(newTcEstimate.isExecuted());
    this.setAccount(newTcEstimate.getAccount());
    this.setCommodity(newTcEstimate.getCommodity());
    this.setBroker(newTcEstimate.getBroker());
    this.setLaycan(newTcEstimate.getLaycan());
    this.setReposition(newTcEstimate.getReposition());
    this.setDate(newTcEstimate.getDate());
    this.setHireRate(newTcEstimate.getHireRate());
    this.setApproxDuration(newTcEstimate.getApproxDuration());
    this.setBallastBonus(newTcEstimate.getBallastBonus());
    this.setCommisionPercent(newTcEstimate.getCommisionPercent());
    this.setBallastDistanceNonSeca(newTcEstimate.getBallastDistanceNonSeca());
    this.setBallastDistanceSeca(newTcEstimate.getBallastDistanceSeca());
    this.setLadenDistanceSeca(newTcEstimate.getLadenDistanceSeca());
    this.setLadenDistanceNonSeca(newTcEstimate.getLadenDistanceNonSeca());
    this.setIfoPrice(newTcEstimate.getIfoPrice());
    this.setMdoPrice(newTcEstimate.getMdoPrice());
    this.setDeliveryCosts(newTcEstimate.getDeliveryCosts());
    this.setRedeliveryCosts(newTcEstimate.getRedeliveryCosts());
    this.setCanalsCost(newTcEstimate.getCanalsCost());
    this.setMiscelCosts(newTcEstimate.getMiscelCosts());
    this.setLostWaitingDays(newTcEstimate.getLostWaitingDays());
    this.setGrossRevenue(newTcEstimate.getGrossRevenue());
    this.setBunkerCost(newTcEstimate.getBunkerCost());
    this.setExpenses(newTcEstimate.getExpenses());
    this.setNetRevenue(newTcEstimate.getNetRevenue());
    this.setSensitivity(newTcEstimate.getSensitivity());
    this.setSensitivityFiveDays(newTcEstimate.getSensitivityFiveDays());
    this.setBbGross(newTcEstimate.getBbGross());
    this.setTotalDuration(newTcEstimate.getTotalDuration());
    this.setTimeCharterRate(newTcEstimate.getTimeCharterRate());
    this.setVessel(newTcEstimate.getVessel());
    return this;
  }
}
