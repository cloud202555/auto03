package com.spring.jwt.entity;

import com.spring.jwt.VehicleReg.VehicleRegDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;


@Entity
@Data
public class VehicleReg {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Integer vehicleRegId;

    @Column
    private Integer appointmentId;

    @Column(length = 45)
    private String chasisNumber;

    @Column(length = 45)
    private String customerAddress;

    @Column(length = 45)
    private String customerAadharNo;

    @Column(length = 45)
    private String customerGstin;

    @Column(length = 45)
    private String superwiser;

    @Column(length = 45)
    private String technician;

    @Column(length = 45)
    private String worker;

    @Column
    private String status;

    @Column
    private Integer userId;

    @Column
    private String date;

    public VehicleReg() {
    }

    public VehicleReg(VehicleRegDto vehicleRegDto) {
        this.vehicleRegId = vehicleRegDto.getVehicleRegId();
        this.appointmentId = vehicleRegDto.getAppointmentId();
        this.chasisNumber = vehicleRegDto.getChasisNumber();
        this.customerAddress = vehicleRegDto.getCustomerAddress();
        this.customerAadharNo = vehicleRegDto.getCustomerAadharNo();
        this.customerGstin = vehicleRegDto.getCustomerGstin();
        this.superwiser = vehicleRegDto.getSuperwiser();
        this.technician = vehicleRegDto.getTechnician();
        this.worker = vehicleRegDto.getWorker();
        this.status = vehicleRegDto.getStatus();
        this.date=vehicleRegDto.getDate();
    }
}
