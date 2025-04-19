package com.spring.jwt.service;

import com.spring.jwt.dto.VendorPartDto;

import java.util.List;

public interface VendorPartService {
    VendorPartDto create(VendorPartDto vendorPartDto);
    VendorPartDto update(Integer id, VendorPartDto vendorPartDto);
    VendorPartDto getById(Integer id);
    List<VendorPartDto> getAll();
    void delete(Integer id);
    List<VendorPartDto> getByPartNumber(String partNumber);
}
