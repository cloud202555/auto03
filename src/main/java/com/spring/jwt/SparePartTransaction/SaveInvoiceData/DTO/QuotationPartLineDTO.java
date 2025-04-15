package com.spring.jwt.SparePartTransaction.SaveInvoiceData.DTO;

import lombok.Data;

@Data
public class QuotationPartLineDTO {
    private Long id;
    private int lineNo;
    private String partName;
    private int quantity;
    private double unitPrice;
    private double discountPercent;
    private double discountAmt;
    private double finalAmount;
}
