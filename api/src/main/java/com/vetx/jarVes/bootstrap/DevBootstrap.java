package com.vetx.jarVes.bootstrap;

import com.vetx.jarVes.model.Role;
import com.vetx.jarVes.model.RoleName;
import com.vetx.jarVes.model.User;
import com.vetx.jarVes.repository.RoleRepository;
import com.vetx.jarVes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    RoleRepository roleRepository;

    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    @Autowired
    public DevBootstrap(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initData();
    }

    private void initData(){

        Role role_admin = new Role(RoleName.ROLE_ADMIN);
        Role role_guest = new Role(RoleName.ROLE_GUEST);
        roleRepository.save(role_admin);
        roleRepository.save(role_guest);

        User user1 = new User("Zan", "Toichi", "zan@email.com", passwordEncoder.encode("123456"));
        User user2 = new User("Thanos", "Louk", "than@email.com", passwordEncoder.encode("654321"));
        User user3 = new User("Pietr", "Piotr", "pietr@email.com", passwordEncoder.encode("1234321"));
        User user4 = new User("Pikos", "Pipikos", "pikos@email.com", passwordEncoder.encode("123454321"));
        User user5 = new User("Kwstas", "Okwsten", "kwsten@email.com", passwordEncoder.encode("12321"));

        user1.setRoles(Collections.singleton(role_admin));
        user2.setRoles(Collections.singleton(role_guest));
        user3.setRoles(Collections.singleton(role_guest));
        user4.setRoles(Collections.singleton(role_guest));
        user5.setRoles(Collections.singleton(role_guest));

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);
    }
}
