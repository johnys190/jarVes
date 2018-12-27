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
    user2.setRoles(Collections.singleton(roleGuest));
    user3.setRoles(Collections.singleton(roleGuest));
    user4.setRoles(Collections.singleton(roleGuest));
    user5.setRoles(Collections.singleton(roleGuest));

    userRepository.save(user1);
    userRepository.save(user524);
    userRepository.save(user2);
    userRepository.save(user3);
    userRepository.save(user4);
    userRepository.save(user5);

    Vessel vessel1 =
        new Vessel(
            "peos",
            1,
            "type1",
            "flag1",
            1,
            "gear1",
            1.1,
            "manager1",
            "pic1",
            1,
            1.1,
            1.1,
            1.1,
            1.1,
            1.1,
            1.1,
            1);
    Vessel vessel2 =
        new Vessel(
            "aidio",
            2,
            "type2",
            "flag2",
            2,
            "gear2",
            2.2,
            "manager2",
            "pic2",
            2,
            2.2,
            2.2,
            2.2,
            2.2,
            2.2,
            2.2,
            2);

    vesselRepository.save(vessel1);
    vesselRepository.save(vessel2);

    TcEstimate tcEstimate1 =
        TcEstimate.builder().name("TcTestimate").account("testaccount").voyage("ath-lon").build();
    tcEstimateRepository.save(tcEstimate1);

    VoyEstimate voyEstimate1 =
        VoyEstimate.builder()
            .name("VoyTestimate")
            .account("testvoyaccount")
            .voyage("ath-lon")
            .build();
    voyEstimateRepository.save(voyEstimate1);
  }
}
