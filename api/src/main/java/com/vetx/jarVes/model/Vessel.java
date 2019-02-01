package com.vetx.jarVes.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

// TODO:Notes - (open a Notepad).

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vessel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  @NonNull
  private String name;

  private Integer dwt;

  private String type;

  private String flag;

  private Integer built;

  private String gear;

  private Double grain;

  private String manager;

  private String pic;

  private Integer speed;

  private Double ifo_ballast;

  private Double ifo_laden;

  private Double port_idle;

  @JsonProperty("port_work")
  private Double port_working;

  private Double mgo_port_idle;

  @JsonProperty("mgo_port_work")
  private Double mgo_port_working;

  private Integer boiler;
}
