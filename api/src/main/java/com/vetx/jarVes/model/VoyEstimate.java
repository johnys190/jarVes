package com.vetx.jarVes.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vetx.jarVes.model.audit.UserDateAudit;
import lombok.*;

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
    public Integer dischDays;
    @JsonProperty("disch_port")
    public String dischPort;
    @JsonProperty("discharge_port_type")
    public String dischargePortType;
    @JsonProperty("discharge_rate_type")
    public String dischargeRateType;
    @JsonProperty("disport_bunkers")
    public Integer disportBunkers;
    @JsonProperty("drate")
    public String drate;
    @JsonProperty("exins")
    public String exins;
    @JsonProperty("expenses")
    public Integer expenses;
    @JsonProperty("extra_costs")
    public String extraCosts;
    @JsonProperty("extra_costs2")
    public String extraCosts2;
    @JsonProperty("freight_rate")
    public String freightRate;
    @JsonProperty("freight_rate_type")
    public String freightRateType;
    @JsonProperty("gross_revenue")
    public Integer grossRevenue;
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
    public Integer loadportBunkers;
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
    public Integer netRevenue;
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
    public Integer sailingBunkers;
    @JsonProperty("seca_ballast")
    public String secaBallast;
    @JsonProperty("seca_laden")
    public String secaLaden;
    @JsonProperty("shex_disch")
    public Integer shexDisch;
    @JsonProperty("shex_load")
    public Integer shexLoad;
    @JsonProperty("speed")
    public String speed;
    @JsonProperty("steaming")
    public Double steaming;
    @JsonProperty("steaming_margin")
    public String steamingMargin;
    @JsonProperty("taxes")
    public Integer taxes;
    @JsonProperty("taxesP")
    public String taxesP;
    @JsonProperty("time_charter_rate")
    public Double timeCharterRate;
    @JsonProperty("total_bunker_cost")
    public Integer totalBunkerCost;
    @JsonProperty("total_duration")
    public Double totalDuration;
    @JsonProperty("voyage")
    public String voyage;

}
