//package com.spring.jwt.SparePartTransaction.SaveInvoiceData;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//
//@Entity
//@Getter
//@Setter
//@Table(name = "invoices")
//public class Invoice {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String invoiceNumber;
//    private LocalDate invoiceDate;
//    private String jobcardNo;
//
//    // Customer/Vehicle fields
//    private String customerName;
//    private String customerAddress;
//    private String customerMobile;
//    private String vehicleNumber;
//    private String engineNumber;
//    private int kmsDriven;
//    private String vehicleBrand;
//    private String vehicleModelName;
//
//    // Summaries
//    private double sparesSubTotal;
//    private double labourSubTotal;
//    private double grandTotal;
//    private double advanceAmount;
//    private double netAmount;
//    private String amountInWords;
//
//    @Column(length = 1024)
//    private String comments;
//
//    // One-to-many relationship with parts line items
//    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<InvoicePartLine> partLines = new ArrayList<>();
//
//    // One-to-many relationship with labour line items
//    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<InvoiceLabourLine> labourLines = new ArrayList<>();
//}
//
