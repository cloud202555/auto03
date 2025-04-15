package com.spring.jwt.SparePartTransaction.SaveInvoiceData;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "quotation")
public class Quotation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String quotationNumber;
    private LocalDate quotationDate;

    private String customerName;
    private String customerAddress;
    private String customerMobile;
    private String vehicleNumber;
    private String customerEmail;

    @OneToMany(mappedBy = "quotation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuotationPartLine> partLines = new ArrayList<>();

    @OneToMany(mappedBy = "quotation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuotationlabourLine> labourLines = new ArrayList<>();
}