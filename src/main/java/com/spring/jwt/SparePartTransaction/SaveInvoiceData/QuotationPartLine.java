package com.spring.jwt.SparePartTransaction.SaveInvoiceData;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "quotation_part_line")
public class QuotationPartLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quotation_id", nullable = false)
    private Quotation quotation;

    private int lineNo;
    private String partName;
    private int quantity;
    private double unitPrice;

    private double discountPercent;
    private double discountAmt;

    private double finalAmount;
}