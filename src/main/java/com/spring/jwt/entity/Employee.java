package com.spring.jwt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

    private String position;

    private String contact;

    private String address;

    private String email;

    private String username;

    @Column(name = "user_id")
    private Integer userId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "employee_component_names", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "component_name")
    private List<String> componentNames;
}
