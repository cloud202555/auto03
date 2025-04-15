package com.spring.jwt.VehicleReg.VehicleServiceUsed;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class VehicleServicesUsed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vehicleServicesUsedId;

    private String serviceName;

    private Integer quantity;

    private LocalDate date;

    private Integer Rate;

    private Integer cGST;

    private Integer sGST;

    private Integer vehicleId;
}
