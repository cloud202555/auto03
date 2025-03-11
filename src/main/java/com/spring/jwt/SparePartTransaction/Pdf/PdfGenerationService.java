//package com.spring.jwt.SparePartTransaction.Pdf;
//
//import com.itextpdf.text.*;
//import com.itextpdf.text.pdf.*;
//
//import com.spring.jwt.SparePartTransaction.SparePartTransaction;
//import com.spring.jwt.SparePartTransaction.SparePartTransactionRepository;
//import com.spring.jwt.VehicleReg.VehicleRegRepository;
//import com.spring.jwt.entity.VehicleReg;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.io.ByteArrayOutputStream;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//
//@Service
//@RequiredArgsConstructor
//public class PdfGenerationService {
//
//    private final VehicleRegRepository vehicleRegRepository;
//    private final SparePartTransactionRepository sparePartRepository;
//
//    /**
//     * Generates the invoice PDF.
//     *
//     * @param vehicleRegId         ID of the vehicle registration record.
//     * @param sparePartId          ID of the spare part record.
//     * @param quantityParam        Quantity for the spare part (as String).
//     * @param discountPercentParam Discount percentage (as String).
//     * @param cGstParam            CGST percentage (as String).
//     * @param sGstParam            SGST percentage (as String).
//     * @param invoiceNumber        Invoice number.
//     * @param jobcardNo            Jobcard number.
//     * @param jobcardDate          Jobcard date (expected format dd-MM-yyyy).
//     * @param kmsDriven            Kilometers driven.
//     * @param slogan               (Optional) Slogan.
//     * @param comments             (Optional) Comments.
//     * @return Byte array containing the generated PDF.
//     * @throws Exception if any error occurs during PDF generation.
//     */
//    public byte[] generatePdf(Integer vehicleRegId,
//                              Integer sparePartId,
//                              String quantityParam,
//                              String discountPercentParam,
//                              String cGstParam,
//                              String sGstParam,
//                              String invoiceNumber,
//                              String jobcardNo,
//                              String jobcardDate, // dd-MM-yyyy
//                              String kmsDriven,
//                              String slogan,
//                              String comments) throws Exception {
//        // 1) Fetch VehicleReg record
//        VehicleReg vehicle = vehicleRegRepository.findById(vehicleRegId)
//                .orElseThrow(() -> new RuntimeException("VehicleReg not found with ID: " + vehicleRegId));
//
//        // 2) Fetch SparePart record
//        SparePartTransaction sparePart = sparePartRepository.findById(sparePartId)
//                .orElseThrow(() -> new RuntimeException("SparePart not found with ID: " + sparePartId));
//
//        int quantity = Integer.parseInt(quantityParam);
//        double discountPercent = Double.parseDouble(discountPercentParam);
//        double cgstRate = Double.parseDouble(cGstParam);
//        double sgstRate = Double.parseDouble(sGstParam);
//
//        double unitPrice = sparePart.getPrice().doubleValue();
//
//        double totalUnitPrice = unitPrice * quantity;
//        double discountAmt = totalUnitPrice * (discountPercent / 100.0);
//        double taxableAmt = totalUnitPrice - discountAmt;
//        double cgstAmt = taxableAmt * (cgstRate / 100.0);
//        double sgstAmt = taxableAmt * (sgstRate / 100.0);
//        double igstRate = 0.0;
//        double igstAmt = 0.0;
//        double finalAmount = taxableAmt + cgstAmt + sgstAmt + igstAmt;
//
//        // 6) Format invoice date from VehicleReg (if null, use current date)
//        LocalDate invDate = vehicle.getDate() != null ? vehicle.getDate() : LocalDate.now();
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        String invoiceDate = invDate.format(dtf);
//
//        // 7) Prepare PDF document
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        Document document = new Document(PageSize.A4, 20, 20, 20, 20);
//        PdfWriter writer = PdfWriter.getInstance(document, baos);
//        writer.setPageEvent(new BorderEvent()); // Draws a border on each page
//        document.open();
//
//        // ------------------------------
//        // (A) HEADER SECTION (Company Info)
//        // ------------------------------
//        Paragraph compName = new Paragraph("AUTO CAR CARE POINT",
//                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
//        compName.setAlignment(Element.ALIGN_CENTER);
//        document.add(compName);
//
//        Paragraph compAddr = new Paragraph("Buvasaheb Nagar, Shingnapur Road, Kolki, Tal.Phaltan(415523), Dist.Satara.\n" +
//                "Ph : 9595054555 / 7758817766      Email : autocarcarepoint@gmail.com\n" +
//                "GSTIN : 27GLYPS9891C1ZV",
//                FontFactory.getFont(FontFactory.HELVETICA, 10));
//        compAddr.setAlignment(Element.ALIGN_CENTER);
//        document.add(compAddr);
//
//        if (slogan != null && !slogan.isEmpty()) {
//            Paragraph sloganPara = new Paragraph(slogan,
//                    FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10));
//            sloganPara.setAlignment(Element.ALIGN_CENTER);
//            document.add(sloganPara);
//        }
//
//        document.add(Chunk.NEWLINE);
//
//        Paragraph taxInvoice = new Paragraph("TAX INVOICE",
//                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14));
//        taxInvoice.setAlignment(Element.ALIGN_CENTER);
//        document.add(taxInvoice);
//
//        document.add(Chunk.NEWLINE);
//
//        // ------------------------------
//        // (B) CUSTOMER & VEHICLE DETAILS TABLE
//        // ------------------------------
//        PdfPTable custVehTable = new PdfPTable(2);
//        custVehTable.setWidthPercentage(100);
//        custVehTable.setWidths(new float[]{50f, 50f});
//        custVehTable.setSpacingBefore(10f);
//
//        // Left cell: CUSTOMER DETAILS (from VehicleReg)
//        PdfPCell custCell = new PdfPCell();
//        custCell.setBorder(Rectangle.NO_BORDER);
//        custCell.addElement(new Paragraph("CUSTOMER DETAILS", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
//        custCell.addElement(new Paragraph("Name : " + vehicle.getCustomerName(), FontFactory.getFont(FontFactory.HELVETICA, 10)));
//        custCell.addElement(new Paragraph("Address : " + vehicle.getCustomerAddress(), FontFactory.getFont(FontFactory.HELVETICA, 10)));
//        custCell.addElement(new Paragraph("Mobile : " + vehicle.getCustomerMobileNumber(), FontFactory.getFont(FontFactory.HELVETICA, 10)));
//        custCell.addElement(new Paragraph("Aadhar No. : " + vehicle.getCustomerAadharNo(), FontFactory.getFont(FontFactory.HELVETICA, 10)));
//        custCell.addElement(new Paragraph("GSTIN : " + vehicle.getCustomerGstin(), FontFactory.getFont(FontFactory.HELVETICA, 10)));
//        custVehTable.addCell(custCell);
//
//        // Right cell: VEHICLE DETAILS
//        PdfPCell vehCell = new PdfPCell();
//        vehCell.setBorder(Rectangle.NO_BORDER);
//        vehCell.addElement(new Paragraph("VEHICLE DETAILS", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
//        vehCell.addElement(new Paragraph("Invoice No : " + invoiceNumber, FontFactory.getFont(FontFactory.HELVETICA, 10)));
//        vehCell.addElement(new Paragraph("Invoice Date : " + invoiceDate, FontFactory.getFont(FontFactory.HELVETICA, 10)));
//        vehCell.addElement(new Paragraph("Reg. No : " + vehicle.getVehicleNumber(), FontFactory.getFont(FontFactory.HELVETICA, 10)));
//        vehCell.addElement(new Paragraph("Model : " + vehicle.getVehicleBrand() + " " + vehicle.getVehicleModelName(), FontFactory.getFont(FontFactory.HELVETICA, 10)));
//        custVehTable.addCell(vehCell);
//
//        document.add(custVehTable);
//
//        // ------------------------------
//        // (C) JOBCARD & EXTRA VEHICLE DETAILS TABLE
//        // ------------------------------
//        PdfPTable jobcardTable = new PdfPTable(4);
//        jobcardTable.setWidthPercentage(100);
//        jobcardTable.setWidths(new float[]{25f, 25f, 25f, 25f});
//        jobcardTable.setSpacingBefore(10f);
//
//        jobcardTable.addCell(detailCell("Jobcard No : " + jobcardNo));
//        jobcardTable.addCell(detailCell("Jobcard Date : " + jobcardDate));
//        jobcardTable.addCell(detailCell("KMS. Driven : " + kmsDriven));
//        jobcardTable.addCell(detailCell("Engine No : " + vehicle.getEngineNumber()));
//
//        document.add(jobcardTable);
//
//        document.add(Chunk.NEWLINE);
//
//        // ------------------------------
//        // (D) SPARES SECTION
//        // ------------------------------
//        Paragraph sparesTitle = new Paragraph("SPARES", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
//        sparesTitle.setAlignment(Element.ALIGN_LEFT);
//        document.add(sparesTitle);
//        document.add(Chunk.NEWLINE);
//
//        // Create SPARES table with 14 columns:
//        // S.No | Perticulars Of Parts | Qty | Unit Price | Discount (%) | Discount Amt | Taxable Amt | CGST % | CGST Amt | SGST % | SGST Amt | IGST % | IGST Amt | Amount
//        PdfPTable sparesTable = new PdfPTable(14);
//        sparesTable.setWidthPercentage(100);
//        sparesTable.setWidths(new float[]{4f, 30f, 4f, 8f, 6f, 8f, 10f, 6f, 8f, 6f, 8f, 6f, 8f, 10f});
//        sparesTable.setSpacingBefore(5f);
//
//        // Header row
//        sparesTable.addCell(headerCell("S.No"));
//        sparesTable.addCell(headerCell("Perticulars Of Parts"));
//        sparesTable.addCell(headerCell("Qty"));
//        sparesTable.addCell(headerCell("Unit Price"));
//        sparesTable.addCell(headerCell("Discount (%)"));
//        sparesTable.addCell(headerCell("Discount Amt"));
//        sparesTable.addCell(headerCell("Taxable Amt"));
//        sparesTable.addCell(headerCell("CGST %"));
//        sparesTable.addCell(headerCell("Amt"));
//        sparesTable.addCell(headerCell("SGST %"));
//        sparesTable.addCell(headerCell("Amt"));
//        sparesTable.addCell(headerCell("IGST %"));
//        sparesTable.addCell(headerCell("Amt"));
//        sparesTable.addCell(headerCell("Amount"));
//
//        sparesTable.addCell(dataCell("1"));
//        String particulars = sparePart.getPartName();
//        sparesTable.addCell(dataCell(particulars));
//        sparesTable.addCell(dataCell(String.valueOf(quantity)));
//        sparesTable.addCell(dataCell(String.format("%.2f", unitPrice)));
//        sparesTable.addCell(dataCell(String.format("%.2f", discountPercent)));
//        sparesTable.addCell(dataCell(String.format("%.2f", discountAmt)));
//        sparesTable.addCell(dataCell(String.format("%.2f", taxableAmt)));
//        sparesTable.addCell(dataCell(String.format("%.2f", cgstRate)));
//        sparesTable.addCell(dataCell(String.format("%.2f", cgstAmt)));
//        sparesTable.addCell(dataCell(String.format("%.2f", sgstRate)));
//        sparesTable.addCell(dataCell(String.format("%.2f", sgstAmt)));
//        sparesTable.addCell(dataCell(String.format("%.2f", igstRate)));
//        sparesTable.addCell(dataCell(String.format("%.2f", igstAmt)));
//        sparesTable.addCell(dataCell(String.format("%.2f", finalAmount)));
//
//        document.add(sparesTable);
//
//        // SUB TOTAL Section
//        Paragraph subTotalPara = new Paragraph("SUB TOTAL " + String.format("%.2f", finalAmount),
//                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10));
//        subTotalPara.setAlignment(Element.ALIGN_RIGHT);
//        subTotalPara.setSpacingBefore(5f);
//        document.add(subTotalPara);
//
//        document.add(Chunk.NEWLINE);
//
//        // ------------------------------
//        // (E) SUMMARY SECTION
//        // ------------------------------
//        PdfPTable summaryTable = new PdfPTable(2);
//        summaryTable.setWidthPercentage(50);
//        summaryTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
//        summaryTable.setSpacingBefore(10f);
//
//        summaryTable.addCell(summaryCell("TOTAL AMOUNT"));
//        summaryTable.addCell(summaryCell(String.format("%.2f", finalAmount)));
//        summaryTable.addCell(summaryCell("ADVANCE AMOUNT"));
//        summaryTable.addCell(summaryCell(String.format("%.2f", 0.00)));
//
//        document.add(summaryTable);
//
//        document.add(Chunk.NEWLINE);
//
//        // Amount in Words
//        Paragraph amountWords = new Paragraph("Total Amount of Invoice in Words:   Rs. "
//                + convertNumberToWords((long) finalAmount) + " only",
//                FontFactory.getFont(FontFactory.HELVETICA, 10));
//        document.add(amountWords);
//
//        document.add(Chunk.NEWLINE);
//
//        // ------------------------------
//        // (F) FOOTER SECTION
//        // ------------------------------
//        Paragraph custNote = new Paragraph("Customer Note:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10));
//        document.add(custNote);
//
//        Paragraph notePara = new Paragraph("Note:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10));
//        document.add(notePara);
//
//        document.add(Chunk.NEWLINE);
//
//        Paragraph scanPay = new Paragraph("Scan To Pay   Customer Signature / Thumb",
//                FontFactory.getFont(FontFactory.HELVETICA, 10));
//        document.add(scanPay);
//
//        document.add(Chunk.NEWLINE);
//
//        Paragraph companyFooter = new Paragraph("Auto Car Care Point",
//                FontFactory.getFont(FontFactory.HELVETICA, 10));
//        document.add(companyFooter);
//
//        Paragraph authSign = new Paragraph("Authorized Signature",
//                FontFactory.getFont(FontFactory.HELVETICA, 10));
//        document.add(authSign);
//
//        Paragraph thanks = new Paragraph("Thank You For Visit.. This is a Computer Generated Invoice | Software Developed by Regex Technologies.",
//                FontFactory.getFont(FontFactory.HELVETICA, 10));
//        thanks.setSpacingBefore(10f);
//        document.add(thanks);
//
//        String footerDate = new SimpleDateFormat("dd/MM/yyyy, HH:mm").format(new java.util.Date());
//        Paragraph footerInfo = new Paragraph(footerDate + " Auto Car Care Point | Tax Invoice",
//                FontFactory.getFont(FontFactory.HELVETICA, 8));
//        footerInfo.setAlignment(Element.ALIGN_CENTER);
//        footerInfo.setSpacingBefore(10f);
//        document.add(footerInfo);
//
//        Paragraph url = new Paragraph("https://autocarcarepoint.com/job?action=printInvoice&veh_reg_id=" + vehicleRegId + " 1/1",
//                FontFactory.getFont(FontFactory.HELVETICA, 8));
//        url.setAlignment(Element.ALIGN_CENTER);
//        document.add(url);
//
//        // Close document
//        document.close();
//        writer.close();
//
//        return baos.toByteArray();
//    }
//
//    // ------------------------------
//    // Helper Methods for Table Cells
//    // ------------------------------
//    private PdfPCell headerCell(String text) {
//        PdfPCell cell = new PdfPCell(new Phrase(text, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 7)));
//        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//        cell.setPadding(3f);
//        return cell;
//    }
//
//    private PdfPCell dataCell(String text) {
//        PdfPCell cell = new PdfPCell(new Phrase(text, FontFactory.getFont(FontFactory.HELVETICA, 7)));
//        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//        cell.setPadding(3f);
//        return cell;
//    }
//
//    private PdfPCell detailCell(String text) {
//        PdfPCell cell = new PdfPCell(new Phrase(text, FontFactory.getFont(FontFactory.HELVETICA, 9)));
//        cell.setBorder(Rectangle.NO_BORDER);
//        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//        cell.setPadding(2f);
//        return cell;
//    }
//
//    private PdfPCell summaryCell(String text) {
//        PdfPCell cell = new PdfPCell(new Phrase(text, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
//        cell.setBorder(Rectangle.NO_BORDER);
//        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//        cell.setPadding(3f);
//        return cell;
//    }
//
//    // ------------------------------
//    // Page Border Event (Draws a border on each page)
//    // ------------------------------
//    private class BorderEvent extends PdfPageEventHelper {
//        @Override
//        public void onEndPage(PdfWriter writer, Document document) {
//            PdfContentByte canvas = writer.getDirectContent();
//            canvas.setLineWidth(1);
//            canvas.setColorStroke(BaseColor.BLACK);
//            float llx = document.left();
//            float lly = document.bottom();
//            float urx = document.right();
//            float ury = document.top();
//            canvas.rectangle(llx, lly, (urx - llx), (ury - lly));
//            canvas.stroke();
//        }
//    }
//
//    // ------------------------------
//    // Simple Number-to-Words Converter
//    // ------------------------------
//    private String convertNumberToWords(long number) {
//        if (number == 0) return "Zero";
//
//        String[] tensNames = {
//                "", " Ten", " Twenty", " Thirty", " Forty",
//                " Fifty", " Sixty", " Seventy", " Eighty", " Ninety"
//        };
//
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
//            words = tensNames[(int) (number / 10)] + ((number % 10 != 0) ? numNames[(int) (number % 10)] : "");
//        } else if (number < 1000) {
//            words = numNames[(int) (number / 100)] + " Hundred" +
//                    ((number % 100 != 0) ? " and " + convertNumberToWords(number % 100) : "");
//        } else if (number < 100000) {
//            words = convertNumberToWords(number / 1000) + " Thousand" +
//                    ((number % 1000 != 0) ? " " + convertNumberToWords(number % 1000) : "");
//        } else if (number < 10000000) {
//            words = convertNumberToWords(number / 100000) + " Lakh" +
//                    ((number % 100000 != 0) ? " " + convertNumberToWords(number % 100000) : "");
//        } else {
//            words = convertNumberToWords(number / 10000000) + " Crore" +
//                    ((number % 10000000 != 0) ? " " + convertNumberToWords(number % 10000000) : "");
//        }
//        return words.trim();
//    }
//}
