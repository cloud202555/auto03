package com.spring.jwt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;


@Entity
@Data
public class Appointment {

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
    private Integer appointmentId;

    @Column(length = 45)
    private String appointmentDate;

    @Column(length = 45)
    private String customerName;

    @Column(length = 45)
    private String mobileNo;

    @Column(length = 45)
    private String vehicleNo;

    @Column(length = 45)
    private String vehicleMaker;

    @Column(length = 45)
    private String vehicleModel;

    @Column(length = 45)
    private String manufacturedYear;

    @Column(length = 45)
    private String kilometerDriven;

    @Column(length = 45)
    private String fuelType;

    @Column(length = 45)
    private String workType;

    @Column(length = 45)
    private String vehicleProblem;

    @Column(length = 45)
    private String pickUpAndDropService;

    @Column
    private Integer userId;

    @Column
    private String status;
}
