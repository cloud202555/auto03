package com.spring.jwt.SparePartTransaction.Pdf;


import lombok.Data;
import java.util.List;

@Data
public class PdfRequest {
    private Integer vehicleRegId;
    private String invoiceNumber;
    private String jobcardNo;
    private String jobcardDate;
    private String kmsDriven;
    private String slogan;
    private Integer advanceAmount;
    private String comments;

    private List<PartDto> parts;
    private List<LabourDto> labours;
}