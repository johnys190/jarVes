package com.vetx.jarVes.bootstrap;

import com.vetx.jarVes.model.*;
import com.vetx.jarVes.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

  private RoleRepository roleRepository;

  private UserRepository userRepository;

  private VesselRepository vesselRepository;

  private PasswordEncoder passwordEncoder;

  private TcEstimateRepository tcEstimateRepository;

  private VoyEstimateRepository voyEstimateRepository;

  @Autowired
  public DevBootstrap(
      RoleRepository roleRepository,
      UserRepository userRepository,
      VesselRepository vesselRepository,
      PasswordEncoder passwordEncoder,
      TcEstimateRepository tcEstimateRepository,
      VoyEstimateRepository voyEstimateRepository) {
    this.roleRepository = roleRepository;
    this.userRepository = userRepository;
    this.vesselRepository = vesselRepository;
    this.passwordEncoder = passwordEncoder;
    this.tcEstimateRepository = tcEstimateRepository;
    this.voyEstimateRepository = voyEstimateRepository;
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    initData();
  }

  private void initData() {

    Role roleAdmin = new Role(RoleName.ROLE_ADMIN);
    Role roleGuest = new Role(RoleName.ROLE_GUEST);
    roleRepository.save(roleAdmin);
    roleRepository.save(roleGuest);

    User user1 = new User("TestName", "TestLN", "test@email.com", passwordEncoder.encode("password"));
    user1.setRoles(Collections.singleton(roleAdmin));
    userRepository.save(user1);

    Vessel vessel1 = Vessel.builder()
        .name("vessel1")
        .dwt(1)
        .type("type1")
        .flag("flag1")
        .built(1)
        .gear("gear1")
        .grain(1.1)
        .manager("manager1")
        .pic("pic1")
        .speed(1234)
        .ifo_ballast(2134.0)
        .ifo_laden(234.0)
        .port_idle(214.0)
        .port_working(34.02)
        .mgo_port_idle(123.0)
        .mgo_port_working(1234.0)
        .boiler(1234)
        .build();
    Vessel vessel3 = Vessel.builder()
        .name("vessel3")
        .dwt(4)
        .type("type4")
        .flag("flag14")
        .built(4)
        .gear("gear4")
        .grain(1.4)
        .manager("manager4")
        .pic("pic4")
        .speed(12345)
        .ifo_ballast(2134.4)
        .ifo_laden(234.4)
        .port_idle(214.4)
        .port_working(34.04)
        .mgo_port_idle(124.0)
        .mgo_port_working(1284.1)
        .boiler(1234)
        .build();
    Vessel vessel4 = Vessel.builder()
        .name("vessel4")
        .dwt(4)
        .type("type4")
        .flag("flag14")
        .built(4)
        .gear("gear4")
        .grain(1.4)
        .manager("manager4")
        .pic("pic4")
        .speed(12345)
        .ifo_ballast(2134.4)
        .ifo_laden(234.4)
        .port_idle(214.4)
        .port_working(34.04)
        .mgo_port_idle(124.0)
        .mgo_port_working(1284.1)
        .boiler(1234)
        .build();
    Vessel vessel2 = Vessel.builder()
        .name("vessel2")
        .dwt(2)
        .type("type2")
        .flag("flag2")
        .built(1)
        .gear("gear2")
        .grain(1.1)
        .manager("manager2")
        .pic("pic2")
        .speed(1234)
        .ifo_ballast(2134.0)
        .ifo_laden(234.0)
        .port_idle(214.0)
        .port_working(34.02)
        .mgo_port_idle(123.0)
        .mgo_port_working(1234.0)
        .boiler(1234)
        .build();

    vesselRepository.save(vessel1);
    vesselRepository.save(vessel2);
    vesselRepository.save(vessel3);
    vesselRepository.save(vessel4);

    TcEstimate tcEstimate1 =
        TcEstimate.builder().name("TcTestimate").account("testaccount").voyage("ath-lon").build();
    tcEstimateRepository.save(tcEstimate1);

    VoyEstimate voyEstimate1 =
            VoyEstimate.builder()
                    .name("VoyTestimate1")
                    .account("testvoyaccount")
                    .voyage("ath-lon")
                    .account("aasd")
                    .boilerPort("1234")
                    .broker("ddfg")
                    .canals("76")
                    .comm("5")
                    .commodity("ddds")
                    .disch("44")
                    .dischDays(4.0)
                    .dischPort("103")
                    .dischargePortType("non_seca")
                    .dischargeRateType("daps")
                    .disportBunkers(3858445.36)
                    .drate("4")
                    .exins("8967")
                    .expenses(9653.0)
                    .extraCosts("455")
                    .extraCosts2("545454")
                    .freightRate("2")
                    .freightRateType("per_mt_intake")
                    .grossRevenue(2.0)
                    .ifoBallast("2134")
                    .ifoLaden("234")
                    .ifoPortIdle("214")
                    .ifoPortWork("34.02")
                    .ifoPrice("67")
                    .laycan("ghjghj")
                    .load("00")
                    .loadDays("0.3333333333333333")
                    .loadPort("754")
                    .loadPortType("seca")
                    .loadRateType("x")
                    .loadportBunkers(45695232.58666666)
                    .lostwaitingDays("990")
                    .lrate("3")
                    .mgoPortIdle("123")
                    .mgoPortWork("1234")
                    .mgoPrice("88")
                    .mgoSea("501")
                    .miscel("90")
                    .name("Test")
                    .netRevenue(-178453256.94666666)
                    .nonSecaBallast("56")
                    .nonSecaLaden("89")
                    .others("556")
                    .quantity("1")
                    .repos("4")
                    .sailingBunkers(22128318.0)
                    .secaBallast("67")
                    .secaLaden("7")
                    .shexDisch(1.0)
                    .shexLoad(330.0)
                    .speed("1234")
                    .steaming(1.8930307941653162)
                    .steamingMargin("255")
                    .taxes(10.0)
                    .taxesP("5")
                    .timeCharterRate(-134628.29968552376)
                    .totalBunkerCost(178443595.94666666)
                    .totalDuration(1327.2263641274985)
                    .voyage("oleeEEEDit")
                    .date("2019-08-31")
                    .vesselName("vessel3")
                    .executed(true)
                    .build();

    voyEstimateRepository.save(voyEstimate1);
    VoyEstimate voyEstimate2 =
            VoyEstimate.builder()
                    .name("VoyTestimate2")
                    .account("testvoyaccount")
                    .voyage("ath-lon")
                    .executed(false)
                    .build();
    voyEstimateRepository.save(voyEstimate2);
  }
}
