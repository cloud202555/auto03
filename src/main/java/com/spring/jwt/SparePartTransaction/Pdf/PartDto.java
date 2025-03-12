package com.spring.jwt.SparePartTransaction.Pdf;

import lombok.Data;

@Data
public class PartDto {
    private String partName;
    private int quantity;
    private double unitPrice;
    private double discountPercent;
    private double cgstPercent;
    private double sgstPercent;
    private double igstPercent;
}