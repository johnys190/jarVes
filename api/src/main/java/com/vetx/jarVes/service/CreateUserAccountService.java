package com.vetx.jarVes.service;

import com.vetx.jarVes.model.Role;
import com.vetx.jarVes.model.RoleName;
import com.vetx.jarVes.model.User;
import com.vetx.jarVes.repository.RoleRepository;
import com.vetx.jarVes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CreateUserAccountService {
  private UserRepository userRepository;

  private PasswordEncoder passwordEncoder;

  private RoleRepository roleRepository;

  @Autowired
  public CreateUserAccountService(
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      RoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.roleRepository = roleRepository;
  }

  // Creating user's account
  public void newUser(String firstName, String lastName, String email, String password) {

    User user = new User(firstName, lastName, email, passwordEncoder.encode(password));
    Role userRole = roleRepository.findByName(RoleName.ROLE_GUEST).get();
    user.setRoles(Collections.singleton(userRole));
    userRepository.save(user);
  }
}
