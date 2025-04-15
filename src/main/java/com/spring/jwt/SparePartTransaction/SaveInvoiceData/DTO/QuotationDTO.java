package com.spring.jwt.SparePartTransaction.SaveInvoiceData.DTO;


import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class QuotationDTO {
    private Long id;
    private String quotationNumber;
    private LocalDate quotationDate;
    private String customerName;
    private String customerAddress;
    private String customerMobile;
    private String vehicleNumber;
    private String customerEmail;
    private List<QuotationPartLineDTO> partLines;
    private List<QuotationLabourLineDTO> labourLines;
}
