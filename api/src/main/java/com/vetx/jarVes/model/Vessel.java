package com.vetx.jarVes.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

// TODO:Notes - (open a Notepad).

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Vessel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

    @Column(unique = true)
    @NonNull
    private String name;

    @NonNull
    private Integer dwt;

  @NonNull private String type;

  @NonNull private String flag;

    @NonNull
    private Integer built;

  @NonNull private String gear;

    @NonNull
    private Double grain;

  @NonNull private String manager;

  @NonNull private String pic;

    @NonNull
    private Integer speed;

    @NonNull
    private Double ifo_ballast;

    @NonNull
    private Double ifo_laden;

    @NonNull
    private Double port_idle;

    @NonNull
    private Double port_working;

    @NonNull
    private Double mgo_port_idle;

    @NonNull
    private Double mgo_port_working;

    @NonNull
    private Integer boiler;
}