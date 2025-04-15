package com.spring.jwt.VehicleReg.VehicleJobCard;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class VehicleJobCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vehicleJobCardId;

    private String jobName;

    private String vehicleNumber;

    private Integer vehicleId;

    private String customerNote;

    private String workShopNote;

    private String jobStatus;

    private LocalDate date;

    private String jobType;

    private Integer jobCardId;
}
