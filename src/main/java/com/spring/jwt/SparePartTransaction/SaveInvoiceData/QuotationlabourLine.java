package com.spring.jwt.SparePartTransaction.SaveInvoiceData;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "invoice_labour_line")
public class QuotationlabourLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    private Quotation quotation;

    private int lineNo;
    private String name;
    private int quantity;
    private double unitPrice;

    private double discountPercent;
    private double discountAmt;

    private double finalAmount;
}
