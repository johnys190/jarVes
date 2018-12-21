package com.vetx.jarVes.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

//TODO:Notes - (open a Notepad).

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Vessel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private int dwt;

    @NonNull
    private String type;

    @NonNull
    private String flag;

    @NonNull
    private int built;

    @NonNull
    private String gear;

    @NonNull
    private double grain;

    @NonNull
    private String manager;

    @NonNull
    private String pic;

    @NonNull
    private int speed;

    @NonNull
    private double ifo_ballast;

    @NonNull
    private double ifo_laden;

    @NonNull
    private double port_idle;

    @NonNull
    private double port_working;

    @NonNull
    private double mgo_port_idle;

    @NonNull
    private double mgo_port_working;

    @NonNull
    private int boiler;
}