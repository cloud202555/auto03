package com.spring.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {

    private Long id;
    private String name;
    private String position;
    private String contact;
    private String address;
    private String email;
    private String username;
    private Integer userId;
    private List<String> componentNames;
}

