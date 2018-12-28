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
  private Long id;

  @Column(unique = true)
  private String name;

  @NonNull private String voyage;

  private String account;

  private String commodity;

  private String broker;

  private String laycan;

  private String reposition;

  private Date date;

  private Double hire_rate;

  private Double approx_dur;

  private Double ballast_bonus;

  private Double commision_percent;

  private Integer ballast_distance_seca;

  private Integer ballast_distance_non_seca;

  private Integer laden_distance_seca;

  private Integer laden_distance_non_seca;

  private Double ifo_price;

  private Double mdo_price;

  private Double delivery_costs;

  private Double redelivery_costs;

  private Double canals_cost;

  private Double miscel_costs;

  private Double lost_waiting_days;

  private Double gross_revenue;

  private Double bunker_cost;

  private Double expenses;

  private Double net_revenue;

  private Integer sensitivity;

  private Double sensitivity_five_days;

  private Integer bb_gross;

  private Double total_duration;

  private Double time_charter_rate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "Vessel_id")
  private Vessel vessel;
}
