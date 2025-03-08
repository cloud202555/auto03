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
public class Sale {

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
    private Integer stockId;

    @Column(nullable = false, length = 45)
    private String invoiceNo;

    @Column(length = 45)
    private String invDate;

    @Column(length = 45)
    private String name;

    @Column(length = 45)
    private String address;

    @Column(length = 45)
    private String mobileNo;

    @Column(length = 45)
    private String gstin;

    @Column(length = 45)
    private String spareName;

    @Column(length = 45)
    private String mrp;

    @Column(length = 45)
    private String rate;

    @Column(length = 45)
    private String qty;

    @Column(length = 45)
    private String gst;



}
