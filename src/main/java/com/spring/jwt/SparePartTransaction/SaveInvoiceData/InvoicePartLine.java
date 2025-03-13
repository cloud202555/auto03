//package com.spring.jwt.SparePartTransaction.SaveInvoiceData;
//
//import lombok.Getter;
//import lombok.Setter;
//
//import jakarta.persistence.*;
//
//@Entity
//@Getter
//@Setter
//@Table(name = "invoice_part_line")
//public class InvoicePartLine {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "invoice_id", nullable = false)
//    private Invoice invoice;
//
//    private int lineNo;
//    private String partName;
//    private int quantity;
//    private double unitPrice;
//
//    private double discountPercent;
//    private double discountAmt;
//
//    private double taxableAmt;
//
//    private double cgstPercent;
//    private double cgstAmt;
//
//    private double sgstPercent;
//    private double sgstAmt;
//
//    private double igstPercent;
//    private double igstAmt;
//
//    private double finalAmount;
//}
