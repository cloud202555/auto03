package com.spring.jwt.SparePartTransaction.SaveInvoiceData.DTO;


import lombok.Data;

@Data
public class QuotationLabourLineDTO {
    private Long id;
    private int lineNo;
    private String name;
    private int quantity;
    private double unitPrice;
    private double discountPercent;
    private double discountAmt;
    private double finalAmount;
}
