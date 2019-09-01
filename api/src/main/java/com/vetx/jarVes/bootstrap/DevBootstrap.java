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

    User user1 = new User("Zan", "Toichi", "zan@email.com", passwordEncoder.encode("123456"));
    User user524 = new User("Peos", "Proktos", "peos@prokt.all", passwordEncoder.encode("poutsa"));
    User user2 = new User("Thanos", "Louk", "than@email.com", passwordEncoder.encode("654321"));
    User user3 = new User("Pietr", "Piotr", "pietr@email.com", passwordEncoder.encode("1234321"));
    User user4 = new User("Pikos", "Pipikos", "pikos@email.com", passwordEncoder.encode("123454321"));
    User user5 = new User("Kwstas", "Okwsten", "kwsten@email.com", passwordEncoder.encode("12321"));

    user1.setRoles(Collections.singleton(roleAdmin));
    user524.setRoles(Collections.singleton(roleAdmin));
    user2.setRoles(Collections.singleton(roleAdmin));
    user3.setRoles(Collections.singleton(roleGuest));
    user4.setRoles(Collections.singleton(roleGuest));
    user5.setRoles(Collections.singleton(roleGuest));

    userRepository.save(user1);
    userRepository.save(user524);
    userRepository.save(user2);
    userRepository.save(user3);
    userRepository.save(user4);
    userRepository.save(user5);

    Vessel vessel1 = Vessel.builder()
        .name("peos")
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
        .name("peot")
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
        .name("peou")
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
        .name("aidio")
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
                    .name("VoyTestimate")
                    .account("testvoyaccount")
                    .voyage("ath-lon")
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
