package com.spring.jwt.SparePartTransaction.SaveInvoiceData.Service;

import com.spring.jwt.SparePartTransaction.Pdf.LabourDto;
import com.spring.jwt.SparePartTransaction.Pdf.PartDto;
import lombok.Data;

import java.util.List;

@Data
public class QuotationRequest {

    private Integer vehicleRegId;
    private String quotationNumber;
    private String comments;
    private double subTotal;
    private double totalAmount;
    private String totalInWords;

    private List<PartDto> parts;
    private List<LabourDto> labours;
}
