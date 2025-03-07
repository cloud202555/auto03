package com.spring.jwt.MapperClasses;

import com.spring.jwt.dto.UserDTO;
import com.spring.jwt.entity.Role;
import com.spring.jwt.entity.User;
import com.spring.jwt.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setMobileNumber(userDTO.getMobileNumber());
        user.setAddress(userDTO.getAddress());

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(userDTO.getRole()));
        user.setRoles(roles);

        return user;
    }
}


