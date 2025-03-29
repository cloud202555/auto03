package com.spring.jwt.MapperClasses;

import com.spring.jwt.vender.Vendor;
import com.spring.jwt.vender.VendorDto;
import org.springframework.stereotype.Component;

@Component
public class VendorMapper {

    public Vendor toEntity (VendorDto vendorDto){
        Vendor vendor = new Vendor();

        vendor.setVendorId(vendorDto.getVendorId());
        vendor.setName(vendorDto.getName());
        vendor.setAddress(vendorDto.getAddress());
        vendor.setGSTno(vendorDto.getGSTno());
        vendor.setMobileNumber(vendorDto.getMobileNumber());
        vendor.setPanNo(vendorDto.getPanNo());

        return vendor;
    }
    public VendorDto toDto(Vendor vendor) {
        VendorDto vendorDto = new VendorDto();
        vendorDto.setVendorId(vendor.getVendorId());
        vendorDto.setName(vendor.getName());
        vendorDto.setAddress(vendor.getAddress());
        vendorDto.setGSTno(vendor.getGSTno());
        vendorDto.setMobileNumber(vendor.getMobileNumber());
        vendorDto.setPanNo(vendor.getPanNo());
        return vendorDto;
    }
}
