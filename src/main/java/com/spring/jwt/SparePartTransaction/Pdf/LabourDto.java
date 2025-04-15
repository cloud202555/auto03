package com.spring.jwt.SparePartTransaction.Pdf;

import lombok.Data;

@Data
public class LabourDto {
    private String description;
    private int quantity;
    private double unitPrice;

    // Unique discount & GST for each labour row
    private double discountPercent;
    private double cgstPercent;
    private double sgstPercent;
    private double igstPercent;
}
