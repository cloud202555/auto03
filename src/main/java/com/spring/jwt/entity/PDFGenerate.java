package com.spring.jwt.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "pdf_generate")
@Getter
@Setter
public class PDFGenerate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer billId;

    @Column(name= "vehicleRegNumber")
    private String vehicleRegNumber;

    @Column(name= "customerName")
    private String customerName;

    @Column(name= "service_charges")
    private String serviceCharges;

    @Column(name= "cGst")
    private String cGst;

    @Column(name= "sGst")
    private String sGst;

    @Column(name= "partsUsed")
    private String partsUsed;

    @Column(name= "billDate")
    private LocalDate billDate;

    @Column(name= "customerContact")
    private String customerContact;

    @Column(name= "totalAmount")
    private String totalAmount;
}
