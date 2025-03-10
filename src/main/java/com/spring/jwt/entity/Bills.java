package com.spring.jwt.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "bills")
@Getter
@Setter
public class Bills {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer billId;

    @Column(name= "vehicleRegNumber")
    private String vehicleRegNumber;

    @Column (name= "serviceCharges")
    private String serviceCharges;

    @Column (name= "charges")
    private String Charges;
}
