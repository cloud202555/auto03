package com.spring.jwt.vender;

import com.spring.jwt.utils.BaseResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VendorService {

     BaseResponseDTO register(VendorDto vendorDto);

     ResponseEntity<?> getUserById(Integer vendorId);

     ResponseEntity<List<VendorDto>> getAllVendors();

     ResponseEntity<VendorDto> UpdateVendor(Integer vendorId, VendorDto vendorDto);

     void deleteVendorById(Integer vendorId);
}
