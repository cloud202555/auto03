package com.spring.jwt.service;

import com.spring.jwt.controller.CustomerDTO;
import com.spring.jwt.dto.UserDTO;
import com.spring.jwt.utils.BaseResponseDTO;

public interface CustomerService {
    BaseResponseDTO registerAccount(CustomerDTO customerDTO);
}
