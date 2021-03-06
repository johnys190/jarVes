package com.vetx.jarVes.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vetx.jarVes.model.audit.UserDateAudit;
import lombok.*;
import org.springframework.util.StringUtils;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoyEstimate extends UserDateAudit {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JsonProperty("account")
  public String account;
  @JsonProperty("boiler_port")
  public String boilerPort;
  @JsonProperty("broker")
  public String broker;
  @JsonProperty("canals")
  public String canals;
  @JsonProperty("comm")
  public String comm;
  @JsonProperty("commodity")
  public String commodity;
  @JsonProperty("disch")
  public String disch;
  @JsonProperty("disch_days")
  public Double dischDays;
  @JsonProperty("disch_port")
  public String dischPort;
  @JsonProperty("discharge_port_type")
  public String dischargePortType;
  @JsonProperty("discharge_rate_type")
  public String dischargeRateType;
  @JsonProperty("disport_bunkers")
  public Double disportBunkers;
  @JsonProperty("drate")
  public String drate;
  @JsonProperty("exins")
  public String exins;
  @JsonProperty("expenses")
  public Double expenses;
  @JsonProperty("extra_costs")
  public String extraCosts;
  @JsonProperty("extra_costs2")
  public String extraCosts2;
  @JsonProperty("freight_rate")
  public String freightRate;
  @JsonProperty("freight_rate_type")
  public String freightRateType;
  @JsonProperty("gross_revenue")
  public Double grossRevenue;
  @JsonProperty("ifo_ballast")
  public String ifoBallast;
  @JsonProperty("ifo_laden")
  public String ifoLaden;
  @JsonProperty("ifo_port_idle")
  public String ifoPortIdle;
  @JsonProperty("ifo_port_work")
  public String ifoPortWork;
  @JsonProperty("ifo_price")
  public String ifoPrice;
  @JsonProperty("laycan")
  public String laycan;
  @JsonProperty("load")
  public String load;
  @JsonProperty("load_days")
  public String loadDays;
  @JsonProperty("load_port")
  public String loadPort;
  @JsonProperty("load_port_type")
  public String loadPortType;
  @JsonProperty("load_rate_type")
  public String loadRateType;
  @JsonProperty("loadport_bunkers")
  public Double loadportBunkers;
  @JsonProperty("lostwaiting_days")
  public String lostwaitingDays;
  @JsonProperty("lrate")
  public String lrate;
  @JsonProperty("mgo_port_idle")
  public String mgoPortIdle;
  @JsonProperty("mgo_port_work")
  public String mgoPortWork;
  @JsonProperty("mgo_price")
  public String mgoPrice;
  @JsonProperty("mgo_sea")
  public String mgoSea;
  @JsonProperty("miscel")
  public String miscel;
  @JsonProperty("name")
  public String name;
  @JsonProperty("net_revenue")
  public Double netRevenue;
  @JsonProperty("non_seca_ballast")
  public String nonSecaBallast;
  @JsonProperty("non_seca_laden")
  public String nonSecaLaden;
  @JsonProperty("others")
  public String others;
  @JsonProperty("quantity")
  public String quantity;
  @JsonProperty("repos")
  public String repos;
  @JsonProperty("sailing_bunkers")
  public Double sailingBunkers;
  @JsonProperty("seca_ballast")
  public String secaBallast;
  @JsonProperty("seca_laden")
  public String secaLaden;
  @JsonProperty("shex_disch")
  public Double shexDisch;
  @JsonProperty("shex_load")
  public Double shexLoad;
  @JsonProperty("speed")
  public String speed;
  @JsonProperty("steaming")
  public Double steaming;
  @JsonProperty("steaming_margin")
  public String steamingMargin;
  @JsonProperty("taxes")
  public Double taxes;
  @JsonProperty("taxesP")
  public String taxesP;
  @JsonProperty("time_charter_rate")
  public Double timeCharterRate;
  @JsonProperty("total_bunker_cost")
  public Double totalBunkerCost;
  @JsonProperty("total_duration")
  public Double totalDuration;
  @JsonProperty("voyage")
  public String voyage;
  @JsonProperty("executed")
  public Boolean executed;
  @JsonProperty("vessel_id")
  public Integer vesselId;
  @JsonProperty("vessel_name")
  public String vesselName;
  @JsonProperty("date")
  public String date;

  private String makeLabel(String s) {
    StringBuilder r = new StringBuilder(StringUtils.capitalize(StringUtils.arrayToDelimitedString(s.split("(?=\\p{Upper})"), " ")));
    r.append(':');
    for (int i = r.length(); i < 25; i++) {
      r.append(" ");
    }
    return r.toString();
  }

  public String toText() {
    return "VoyEstimate\n" +
        makeLabel("account") + account + '\n' +
        makeLabel("boilerPort") + boilerPort + '\n' +
        makeLabel("broker") + broker + '\n' +
        makeLabel("canals") + canals + '\n' +
        makeLabel("comm") + comm + '\n' +
        makeLabel("commodity") + commodity + '\n' +
        makeLabel("disch") + disch + '\n' +
        makeLabel("dischDays") + dischDays + '\n' +
        makeLabel("dischPort") + dischPort + '\n' +
        makeLabel("dischargePortType") + dischargePortType + '\n' +
        makeLabel("dischargeRateType") + dischargeRateType + '\n' +
        makeLabel("disportBunkers") + disportBunkers + '\n' +
        makeLabel("drate") + drate + '\n' +
        makeLabel("exins") + exins + '\n' +
        makeLabel("expenses") + expenses + '\n' +
        makeLabel("extraCosts") + extraCosts + '\n' +
        makeLabel("extraCosts2") + extraCosts2 + '\n' +
        makeLabel("freightRate") + freightRate + '\n' +
        makeLabel("freightRateType") + freightRateType + '\n' +
        makeLabel("grossRevenue") + grossRevenue + '\n' +
        makeLabel("ifoBallast") + ifoBallast + '\n' +
        makeLabel("ifoLaden") + ifoLaden + '\n' +
        makeLabel("ifoPortIdle") + ifoPortIdle + '\n' +
        makeLabel("ifoPortWork") + ifoPortWork + '\n' +
        makeLabel("ifoPrice") + ifoPrice + '\n' +
        makeLabel("laycan") + laycan + '\n' +
        makeLabel("load") + load + '\n' +
        makeLabel("loadDays") + loadDays + '\n' +
        makeLabel("loadPort") + loadPort + '\n' +
        makeLabel("loadPortType") + loadPortType + '\n' +
        makeLabel("loadRateType") + loadRateType + '\n' +
        makeLabel("loadPortBunkers") + loadportBunkers + '\n' +
        makeLabel("lostWaitingDays") + lostwaitingDays + '\n' +
        makeLabel("lrate") + lrate + '\n' +
        makeLabel("mgoPortIdle") + mgoPortIdle + '\n' +
        makeLabel("mgoPortWork") + mgoPortWork + '\n' +
        makeLabel("mgoPrice") + mgoPrice + '\n' +
        makeLabel("mgoSea") + mgoSea + '\n' +
        makeLabel("miscel") + miscel + '\n' +
        makeLabel("name") + name + '\n' +
        makeLabel("netRevenue") + netRevenue + '\n' +
        makeLabel("nonSecaBallast") + nonSecaBallast + '\n' +
        makeLabel("nonSecaLaden") + nonSecaLaden + '\n' +
        makeLabel("others") + others + '\n' +
        makeLabel("quantity") + quantity + '\n' +
        makeLabel("repos") + repos + '\n' +
        makeLabel("sailingBunkers") + sailingBunkers + '\n' +
        makeLabel("secaBallast") + secaBallast + '\n' +
        makeLabel("secaLaden") + secaLaden + '\n' +
        makeLabel("shexDisch") + shexDisch + '\n' +
        makeLabel("shexLoad") + shexLoad + '\n' +
        makeLabel("speed") + speed + '\n' +
        makeLabel("steaming") + steaming + '\n' +
        makeLabel("steamingMargin") + steamingMargin + '\n' +
        makeLabel("taxes") + taxes + '\n' +
        makeLabel("taxes %") + taxesP + '\n' +
        makeLabel("timeCharterRate") + timeCharterRate + '\n' +
        makeLabel("totalBunkerCost") + totalBunkerCost + '\n' +
        makeLabel("totalDuration") + totalDuration + '\n' +
        makeLabel("voyage") + voyage + '\n' +
        makeLabel("executed") + executed + '\n' +
        makeLabel("vesselName") + vesselName + '\n' +
        makeLabel("date") + date;
  }
}