package com.vetx.jarVes.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//TODO:Notes - (na anoigei ena notepad).

@Entity
@Data
public class Vessel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int dwt;

    private String type;

    private String flag;

    private int built;

    private String gear;

    private double grain;

    private String manager;

    private String pic;

    private int speed;

    private double ifo_ballast;

    private double ifo_laden;

    private double port_idle;

    private double port_working;

    private double mgo_port_idle;

    private double mgo_port_working;

    private int boiler;





}
