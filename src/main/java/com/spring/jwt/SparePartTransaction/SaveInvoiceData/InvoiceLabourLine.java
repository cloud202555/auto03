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
//@Table(name = "invoice_labour_line")
//public class InvoiceLabourLine {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    // Link back to the invoice
//    @ManyToOne
//    @JoinColumn(name = "invoice_id", nullable = false)
//    private Invoice invoice;
//
//    // Columns for each piece of data in the "Labour Work" table
//    private int lineNo; // S.No
//    private String description; // Particulars Of Services
//    private int quantity;
//    private double unitPrice;
//
//    // Discount
//    private double discountPercent;
//    private double discountAmt;
//
//    // Taxable amount
//    private double taxableAmt;
//
//    // CGST
//    private double cgstPercent;
//    private double cgstAmt;
//
//    // SGST
//    private double sgstPercent;
//    private double sgstAmt;
//
//    // IGST
//    private double igstPercent;
//    private double igstAmt;
//
//    // Final amount
//    private double finalAmount;
//}
