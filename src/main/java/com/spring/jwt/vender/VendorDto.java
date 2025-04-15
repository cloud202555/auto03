package com.spring.jwt.vender;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendorDto {

    private Integer vendorId;

    private String name;

    private String GSTno;

    private String address;

    private Long mobileNumber;

    private String panNo;

    private String SpareBrand;
}

