//package com.spring.jwt.SparePartTransaction.SaveInvoiceData.Service;
//
//import com.spring.jwt.SparePartTransaction.Pdf.PDFEntity.CounterService;
//import com.spring.jwt.SparePartTransaction.Pdf.PartDto;
//import com.spring.jwt.SparePartTransaction.Pdf.LabourDto;
//import com.spring.jwt.SparePartTransaction.Pdf.PdfRequest;
//import com.spring.jwt.SparePartTransaction.SaveInvoiceData.InvoiceLabourLine;
//import com.spring.jwt.SparePartTransaction.SaveInvoiceData.InvoicePartLine;
//import com.spring.jwt.SparePartTransaction.SaveInvoiceData.Quotation;
//import com.spring.jwt.SparePartTransaction.SaveInvoiceData.Repository.InvoiceRepository;
//import com.spring.jwt.VehicleReg.VehicleRegRepository;
//import com.spring.jwt.entity.VehicleReg;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//
//@Service
//@RequiredArgsConstructor
//public class InvoiceService {
//
//    private final VehicleRegRepository vehicleRegRepository;
//    private final InvoiceRepository invoiceRepository;
//    private final CounterService counterService;
//
//    public Quotation saveInvoice(QuotationRequest request) {
//
//        VehicleReg vehicle = vehicleRegRepository.findById(request.getVehicleRegId())
//                .orElseThrow(() -> new RuntimeException("VehicleReg not found with ID: " + request.getVehicleRegId()));
//
//        String quotationNumber = request.getQuotationNumber();
//        if (quotationNumber == null || quotationNumber.trim().isEmpty()) {
//            quotationNumber = String.valueOf(counterService.getNextCounter("quotation", 1));
//            request.getQuotationNumber(quotationNumber);
//        }
//
//        Quotation quotation = new Quotation();
//        quotation.setQuotationNumber(quotationNumber);
//
//        LocalDate invDate = (vehicle.getDate() != null) ? vehicle.getDate() : LocalDate.now();
//        quotation.setInvoiceDate(invDate);
//
//        quotation.setCustomerName(vehicle.getCustomerName());
//        quotation.setCustomerAddress(vehicle.getCustomerAddress());
//        quotation.setCustomerMobile(vehicle.getCustomerMobileNumber());
//        quotation.setVehicleNumber(vehicle.getVehicleNumber());
//        quotation.setEngineNumber(vehicle.getEngineNumber());
//        quotation.setKmsDriven(vehicle.getKmsDriven());
//        quotation.setVehicleBrand(vehicle.getVehicleBrand());
//        quotation.setVehicleModelName(vehicle.getVehicleModelName());
//
//        quotation.setComments(request.getComments());
//
//        double sparesSubTotal = 0.0;
//        List<InvoicePartLine> partLines = new ArrayList<>();
//        int sNo = 1;
//
//        if (request.getParts() != null) {
//            for (PartDto partDto : request.getParts()) {
//                InvoicePartLine partLine = new InvoicePartLine();
//                partLine.setQuotation(quotation);
//                partLine.setLineNo(sNo++);
//                partLine.setPartName(partDto.getPartName());
//                partLine.setQuantity(partDto.getQuantity());
//                partLine.setUnitPrice(partDto.getUnitPrice());
//
//                // Perform calculations
//                double totalPrice = partDto.getQuantity() * partDto.getUnitPrice();
//                double discountAmt = totalPrice * (partDto.getDiscountPercent() / 100.0);
//                double taxableAmt = totalPrice - discountAmt;
//                double cgstAmt = taxableAmt * (partDto.getCgstPercent() / 100.0);
//                double sgstAmt = taxableAmt * (partDto.getSgstPercent() / 100.0);
//                double igstAmt = taxableAmt * (partDto.getIgstPercent() / 100.0);
//                double finalAmount = taxableAmt + cgstAmt + sgstAmt + igstAmt;
//
//                // Set line columns
//                partLine.setDiscountPercent(partDto.getDiscountPercent());
//                partLine.setDiscountAmt(discountAmt);
//                partLine.setTaxableAmt(taxableAmt);
//                partLine.setCgstPercent(partDto.getCgstPercent());
//                partLine.setCgstAmt(cgstAmt);
//                partLine.setSgstPercent(partDto.getSgstPercent());
//                partLine.setSgstAmt(sgstAmt);
//                partLine.setIgstPercent(partDto.getIgstPercent());
//                partLine.setIgstAmt(igstAmt);
//                partLine.setFinalAmount(finalAmount);
//
//                sparesSubTotal += finalAmount;
//                partLines.add(partLine);
//            }
//        }
//
//        quotation.setPartLines(partLines);
//        quotation.setSparesSubTotal(sparesSubTotal);
//
//        double labourSubTotal = 0.0;
//        List<InvoiceLabourLine> labourLines = new ArrayList<>();
//        sNo = 1;
//
//        if (request.getLabours() != null) {
//            for (LabourDto labourDto : request.getLabours()) {
//                InvoiceLabourLine labourLine = new InvoiceLabourLine();
//                labourLine.setInvoice(quotation);
//                labourLine.setLineNo(sNo++);
//                labourLine.setDescription(labourDto.getDescription());
//                labourLine.setQuantity(labourDto.getQuantity());
//                labourLine.setUnitPrice(labourDto.getUnitPrice());
//
//                double totalPrice = labourDto.getQuantity() * labourDto.getUnitPrice();
//                double discountAmt = totalPrice * (labourDto.getDiscountPercent() / 100.0);
//                double taxableAmt = totalPrice - discountAmt;
//                double cgstAmt = taxableAmt * (labourDto.getCgstPercent() / 100.0);
//                double sgstAmt = taxableAmt * (labourDto.getSgstPercent() / 100.0);
//                double igstAmt = taxableAmt * (labourDto.getIgstPercent() / 100.0);
//                double finalAmount = taxableAmt + cgstAmt + sgstAmt + igstAmt;
//
//                labourLine.setDiscountPercent(labourDto.getDiscountPercent());
//                labourLine.setDiscountAmt(discountAmt);
//                labourLine.setTaxableAmt(taxableAmt);
//                labourLine.setCgstPercent(labourDto.getCgstPercent());
//                labourLine.setCgstAmt(cgstAmt);
//                labourLine.setSgstPercent(labourDto.getSgstPercent());
//                labourLine.setSgstAmt(sgstAmt);
//                labourLine.setIgstPercent(labourDto.getIgstPercent());
//                labourLine.setIgstAmt(igstAmt);
//                labourLine.setFinalAmount(finalAmount);
//
//                labourSubTotal += finalAmount;
//                labourLines.add(labourLine);
//            }
//        }
//
//        quotation.setLabourLines(labourLines);
//        quotation.setLabourSubTotal(labourSubTotal);
//
//        // 6) Final totals
//        double grandTotal = sparesSubTotal + labourSubTotal;
//        double advAmount = request.getAdvanceAmount();
//        double netAmount = grandTotal;
//
//        if (advAmount > 0) {
//            netAmount = grandTotal - advAmount;
//        }
//        if (netAmount < 0) {
//            throw new IllegalArgumentException("Advance amount cannot exceed total amount.");
//        }
//
//        quotation.setGrandTotal(grandTotal);
//        quotation.setAdvanceAmount(advAmount);
//        quotation.setNetAmount(netAmount);
//
//        // Convert net amount to words (same logic as PDF)
//        String amountInWords = convertNumberToWords((long) netAmount) + " only";
//        quotation.setAmountInWords(amountInWords);
//
//        // 7) Save the quotation (this will cascade and save lines)
//        return invoiceRepository.save(quotation);
//    }
//
//    // Helper to convert number to words
//    private String convertNumberToWords(long number) {
//        if (number == 0) return "Zero";
//        String[] tensNames = {
//                "", " Ten", " Twenty", " Thirty", " Forty",
//                " Fifty", " Sixty", " Seventy", " Eighty", " Ninety"
//        };
//        String[] numNames = {
//                "", " One", " Two", " Three", " Four", " Five", " Six",
//                " Seven", " Eight", " Nine", " Ten", " Eleven", " Twelve",
//                " Thirteen", " Fourteen", " Fifteen", " Sixteen", " Seventeen",
//                " Eighteen", " Nineteen"
//        };
//
//        String words;
//        if (number < 20) {
//            words = numNames[(int) number];
//        } else if (number < 100) {
//            words = tensNames[(int) (number / 10)]
//                    + ((number % 10 != 0) ? numNames[(int) (number % 10)] : "");
//        } else if (number < 1000) {
//            words = numNames[(int) (number / 100)] + " Hundred"
//                    + ((number % 100 != 0) ? " and " + convertNumberToWords(number % 100) : "");
//        } else if (number < 100000) {
//            words = convertNumberToWords(number / 1000) + " Thousand"
//                    + ((number % 1000 != 0) ? " " + convertNumberToWords(number % 1000) : "");
//        } else if (number < 10000000) {
//            words = convertNumberToWords(number / 100000) + " Lakh"
//                    + ((number % 100000 != 0) ? " " + convertNumberToWords(number % 100000) : "");
//        } else {
//            words = convertNumberToWords(number / 10000000) + " Crore"
//                    + ((number % 10000000 != 0) ? " " + convertNumberToWords(number % 10000000) : "");
//        }
//        return words.trim();
//    }
//}
