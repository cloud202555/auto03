package com.spring.jwt.service.impl;

import com.spring.jwt.controller.CustomerDTO;
import com.spring.jwt.entity.Role;
import com.spring.jwt.entity.User;
import com.spring.jwt.repository.RoleRepository;
import com.spring.jwt.repository.UserRepository;
import com.spring.jwt.service.CustomerService;
import com.spring.jwt.utils.BaseResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public CustomerServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public BaseResponseDTO registerAccount(CustomerDTO customerDTO) {
        User existingUser = userRepository.findByEmail(customerDTO.getEmail());
        if (existingUser != null) {
            return new BaseResponseDTO(
                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    "User already registered with email: " + customerDTO.getEmail()
            );
        }

        User user = customerDTO.toEntity();

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            user.setPassword("ashytu@7645");
        }

        String roleName = (customerDTO.getRole() != null && !customerDTO.getRole().isBlank())
                ? customerDTO.getRole() : "USER";
        Role userRole = roleRepository.findByName(roleName);
        if (userRole == null) {
            Role role = new Role();
            role.setName(roleName);
            userRole = roleRepository.save(role);
        }
        user.setRoles(Collections.singleton(userRole));

        userRepository.save(user);

        return new BaseResponseDTO(
                String.valueOf(HttpStatus.OK.value()),
                "User registered successfully!"
        );
    }

}
