package com.vetx.jarVes.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*NAME

PIC
Notes (na anoigei ena notepad)

SPEED
IFO-BALLAST
IFO-LADEN
PORT IDLE
PORT WORKING
MGO PORT IDLE
MGO PORT WORKING
BOILER*/

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

}
