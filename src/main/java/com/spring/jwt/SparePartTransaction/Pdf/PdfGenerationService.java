package com.spring.jwt.SparePartTransaction.Pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.spring.jwt.SparePartTransaction.Pdf.PDFEntity.CounterService;
import com.spring.jwt.VehicleReg.VehicleRegRepository;
import com.spring.jwt.entity.VehicleReg;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PdfGenerationService {

    private final VehicleRegRepository vehicleRegRepository;
    private final CounterService counterService;

    public byte[] generatePdf(PdfRequest request) throws Exception {

        // Ensure parts and labours lists are not null
        if (request.getParts() == null) {
            request.setParts(new ArrayList<>());
        }
        if (request.getLabours() == null) {
            request.setLabours(new ArrayList<>());
        }

        // Retrieve vehicle details
        VehicleReg vehicle = vehicleRegRepository.findById(request.getVehicleRegId())
                .orElseThrow(() -> new RuntimeException("VehicleReg not found with ID: " + request.getVehicleRegId()));

        LocalDate invDate = (vehicle.getDate() != null) ? vehicle.getDate() : LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String invoiceDate = invDate.format(dtf);
      String generatedInvoiceNumber;
        if (request.getInvoiceNumber() == null || request.getInvoiceNumber().trim().isEmpty()) {
            generatedInvoiceNumber = String.valueOf(counterService.getNextCounter("invoice", 1000));
            request.setInvoiceNumber(generatedInvoiceNumber);
        } else {
            generatedInvoiceNumber = request.getInvoiceNumber();
        }

        String generatedJobCardNumber;
        if (request.getJobcardNo() == null || request.getJobcardNo().trim().isEmpty()) {
            generatedJobCardNumber = String.valueOf(counterService.getNextCounter("jobCard", 100));
            request.setJobcardNo(generatedJobCardNumber);
        } else {
            generatedJobCardNumber = request.getJobcardNo();
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 20, 20, 20, 20);
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        writer.setPageEvent(new BorderEvent());
        document.open();

        PdfPTable topLineTable = new PdfPTable(2);
        topLineTable.setWidthPercentage(100f);
        topLineTable.setWidths(new float[]{50f, 50f});
        topLineTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        String dateTimeString = new SimpleDateFormat("dd/MM/yyyy, HH:mm").format(new java.util.Date());
        PdfPCell leftCell = new PdfPCell(new Phrase(dateTimeString,
                FontFactory.getFont(FontFactory.HELVETICA, 9)));
        leftCell.setBorder(Rectangle.NO_BORDER);
        leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        topLineTable.addCell(leftCell);

        PdfPCell rightCell = new PdfPCell(new Phrase("Auto Car Care Point | Tax Invoice",
                FontFactory.getFont(FontFactory.HELVETICA, 9)));
        rightCell.setBorder(Rectangle.NO_BORDER);
        rightCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        topLineTable.addCell(rightCell);
        document.add(topLineTable);

        // ---------------------------------------------------
        // (B) HEADER BOX: COMPANY INFO (No Slogan)
        // ---------------------------------------------------
        PdfPTable headerBox = new PdfPTable(1);
        headerBox.setWidthPercentage(100f);

        PdfPCell headerCell = new PdfPCell();
        headerCell.setBorder(Rectangle.BOX);
        headerCell.setPadding(5f);
        Paragraph compName = new Paragraph("AUTO CAR CARE POINT",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11));
        Paragraph compAddr = new Paragraph(
                "Buvasaheb Nagar, Shingnapur Road, Kolki, Tal.Phaltan(415523), Dist.Satara.\n" +
                        "Ph : 9595054555 / 7758817766   Email : autocarcarepoint@gmail.com\n" +
                        "GSTIN : 27GLYPS9891C1ZV",
                FontFactory.getFont(FontFactory.HELVETICA, 9));
        headerCell.addElement(compName);
        headerCell.addElement(compAddr);
        headerBox.addCell(headerCell);
        document.add(headerBox);

        // ---------------------------------------------------
        // (C) CUSTOMER & VEHICLE DETAILS
        // ---------------------------------------------------
        PdfPTable custVehTable = new PdfPTable(2);
        custVehTable.setWidthPercentage(100);
        custVehTable.setWidths(new float[]{50f, 50f});
        custVehTable.setSpacingBefore(0f); // No extra space
        custVehTable.setHeaderRows(1);

        PdfPCell customerHeader = new PdfPCell(new Phrase("CUSTOMER DETAILS",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
        customerHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        customerHeader.setVerticalAlignment(Element.ALIGN_MIDDLE);
        customerHeader.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
        custVehTable.addCell(customerHeader);

        PdfPCell vehicleHeader = new PdfPCell(new Phrase("VEHICLE DETAILS",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
        vehicleHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        vehicleHeader.setVerticalAlignment(Element.ALIGN_MIDDLE);
        vehicleHeader.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
        custVehTable.addCell(vehicleHeader);

        PdfPCell customerData = new PdfPCell();
        customerData.setPadding(5f);
        customerData.addElement(new Paragraph("Name : " + vehicle.getCustomerName(),
                FontFactory.getFont(FontFactory.HELVETICA, 9)));
        customerData.addElement(new Paragraph("Address : " + vehicle.getCustomerAddress(),
                FontFactory.getFont(FontFactory.HELVETICA, 9)));
        customerData.addElement(new Paragraph("Mobile : " + vehicle.getCustomerMobileNumber(),
                FontFactory.getFont(FontFactory.HELVETICA, 9)));
        custVehTable.addCell(customerData);

        PdfPCell vehicleData = new PdfPCell();
        vehicleData.setPadding(5f);
        vehicleData.addElement(new Paragraph("Invoice No : " + generatedInvoiceNumber,
                FontFactory.getFont(FontFactory.HELVETICA, 9)));
        vehicleData.addElement(new Paragraph("Invoice Date : " + invoiceDate,
                FontFactory.getFont(FontFactory.HELVETICA, 9)));
        vehicleData.addElement(new Paragraph("Reg. No : " + vehicle.getVehicleNumber(),
                FontFactory.getFont(FontFactory.HELVETICA, 9)));
        vehicleData.addElement(new Paragraph("Engine No : " + vehicle.getEngineNumber(),
                FontFactory.getFont(FontFactory.HELVETICA, 9)));
        vehicleData.addElement(new Paragraph("KMs Driven : " + String.valueOf(vehicle.getKmsDriven()),
                FontFactory.getFont(FontFactory.HELVETICA, 9)));
        vehicleData.addElement(new Paragraph("Model : " + vehicle.getVehicleBrand() + " - " + vehicle.getVehicleModelName(),
                FontFactory.getFont(FontFactory.HELVETICA, 9)));
        vehicleData.addElement(new Paragraph("Jobcard No : " + generatedJobCardNumber,
                FontFactory.getFont(FontFactory.HELVETICA, 9)));
        custVehTable.addCell(vehicleData);
        document.add(custVehTable);

        // ---------------------------------------------------
        // (D) SPARES TABLE
        // ---------------------------------------------------
        Paragraph sparesHeading = new Paragraph("SPARES",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
        sparesHeading.setSpacingAfter(2f);
        document.add(sparesHeading);

        PdfPTable sparesTable = new PdfPTable(14);
        sparesTable.setWidthPercentage(100);
        sparesTable.setSpacingBefore(2f);
        sparesTable.setWidths(new float[]{
                4f, 30f, 4f, 8f,
                6f, 8f,
                10f,
                6f, 8f,
                6f, 8f,
                6f, 8f,
                10f
        });
        sparesTable.setHeaderRows(2);
        PdfPCell sNoHeader = headerCell("S.No");
        sNoHeader.setRowspan(2);
        sparesTable.addCell(sNoHeader);
        PdfPCell partHeader = headerCell("Perticulars Of Parts");
        partHeader.setRowspan(2);
        sparesTable.addCell(partHeader);
        PdfPCell qtyHeader = headerCell("Qty");
        qtyHeader.setRowspan(2);
        sparesTable.addCell(qtyHeader);
        PdfPCell unitPriceHeader = headerCell("Unit Price");
        unitPriceHeader.setRowspan(2);
        sparesTable.addCell(unitPriceHeader);
        PdfPCell discountHeader = headerCell("Discount");
        discountHeader.setColspan(2);
        sparesTable.addCell(discountHeader);
        PdfPCell taxableAmtHeader = headerCell("Taxable Amt");
        taxableAmtHeader.setRowspan(2);
        sparesTable.addCell(taxableAmtHeader);
        PdfPCell cgstHeader = headerCell("CGST");
        cgstHeader.setColspan(2);
        sparesTable.addCell(cgstHeader);
        PdfPCell sgstHeader = headerCell("SGST");
        sgstHeader.setColspan(2);
        sparesTable.addCell(sgstHeader);
        PdfPCell igstHeader = headerCell("IGST");
        igstHeader.setColspan(2);
        sparesTable.addCell(igstHeader);
        PdfPCell amountHeader = headerCell("Amount");
        amountHeader.setRowspan(2);
        sparesTable.addCell(amountHeader);
        sparesTable.addCell(headerCell("%"));
        sparesTable.addCell(headerCell("Amt"));
        sparesTable.addCell(headerCell("%"));
        sparesTable.addCell(headerCell("Amt"));
        sparesTable.addCell(headerCell("%"));
        sparesTable.addCell(headerCell("Amt"));
        sparesTable.addCell(headerCell("%"));
        sparesTable.addCell(headerCell("Amt"));
        double sparesSubTotal = 0.0;
        int sNo = 1;
        for (PartDto part : request.getParts()) {
            int qty = part.getQuantity();
            double price = part.getUnitPrice();
            double discPercent = part.getDiscountPercent();
            double cgstRate = part.getCgstPercent();
            double sgstRate = part.getSgstPercent();
            double igstRate = part.getIgstPercent();
            double totalPrice = price * qty;
            double discountAmt = totalPrice * (discPercent / 100.0);
            double taxableAmt = totalPrice - discountAmt;
            double cgstAmt = taxableAmt * (cgstRate / 100.0);
            double sgstAmt = taxableAmt * (sgstRate / 100.0);
            double igstAmt = taxableAmt * (igstRate / 100.0);
            double finalAmount = taxableAmt + cgstAmt + sgstAmt + igstAmt;
            sparesSubTotal += finalAmount;
            sparesTable.addCell(dataCell(String.valueOf(sNo++)));
            sparesTable.addCell(dataCell(part.getPartName()));
            sparesTable.addCell(dataCell(String.valueOf(qty)));
            sparesTable.addCell(dataCell(String.format("%.2f", price)));
            sparesTable.addCell(dataCell(String.format("%.2f", discPercent)));
            sparesTable.addCell(dataCell(String.format("%.2f", discountAmt)));
            sparesTable.addCell(dataCell(String.format("%.2f", taxableAmt)));
            sparesTable.addCell(dataCell(String.format("%.2f", cgstRate)));
            sparesTable.addCell(dataCell(String.format("%.2f", cgstAmt)));
            sparesTable.addCell(dataCell(String.format("%.2f", sgstRate)));
            sparesTable.addCell(dataCell(String.format("%.2f", sgstAmt)));
            sparesTable.addCell(dataCell(String.format("%.2f", igstRate)));
            sparesTable.addCell(dataCell(String.format("%.2f", igstAmt)));
            sparesTable.addCell(dataCell(String.format("%.2f", finalAmount)));
        }
        PdfPCell sparesSubLabel = new PdfPCell(new Phrase("SUB TOTAL",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9)));
        sparesSubLabel.setColspan(13);
        sparesSubLabel.setHorizontalAlignment(Element.ALIGN_RIGHT);
        sparesTable.addCell(sparesSubLabel);
        PdfPCell sparesSubValue = new PdfPCell(new Phrase(String.format("%.2f", sparesSubTotal),
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9)));
        sparesSubValue.setHorizontalAlignment(Element.ALIGN_RIGHT);
        sparesTable.addCell(sparesSubValue);
        document.add(sparesTable);

        // ---------------------------------------------------
        // (E) LABOUR TABLE
        // ---------------------------------------------------
        Paragraph labourHeading = new Paragraph("LABOUR WORK",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
        labourHeading.setSpacingAfter(2f);
        document.add(labourHeading);
        PdfPTable labourTable = new PdfPTable(14);
        labourTable.setWidthPercentage(100);
        labourTable.setSpacingBefore(2f);
        labourTable.setWidths(new float[]{
                4f, 30f, 4f, 8f,
                6f, 8f,
                10f,
                6f, 8f,
                6f, 8f,
                6f, 8f,
                10f
        });
        labourTable.setHeaderRows(2);
        PdfPCell lSNoHeader = headerCell("S.No");
        lSNoHeader.setRowspan(2);
        labourTable.addCell(lSNoHeader);
        PdfPCell lDescHeader = headerCell("Perticulars Of Services");
        lDescHeader.setRowspan(2);
        labourTable.addCell(lDescHeader);
        PdfPCell lQtyHeader = headerCell("Qty");
        lQtyHeader.setRowspan(2);
        labourTable.addCell(lQtyHeader);
        PdfPCell lUnitPriceHeader = headerCell("Unit Price");
        lUnitPriceHeader.setRowspan(2);
        labourTable.addCell(lUnitPriceHeader);
        PdfPCell lDiscountHeader = headerCell("Discount");
        lDiscountHeader.setColspan(2);
        labourTable.addCell(lDiscountHeader);
        PdfPCell lTaxableAmtHeader = headerCell("Taxable Amt");
        lTaxableAmtHeader.setRowspan(2);
        labourTable.addCell(lTaxableAmtHeader);
        PdfPCell lCgstHeader = headerCell("CGST");
        lCgstHeader.setColspan(2);
        labourTable.addCell(lCgstHeader);
        PdfPCell lSgstHeader = headerCell("SGST");
        lSgstHeader.setColspan(2);
        labourTable.addCell(lSgstHeader);
        PdfPCell lIgstHeader = headerCell("IGST");
        lIgstHeader.setColspan(2);
        labourTable.addCell(lIgstHeader);
        PdfPCell lAmountHeader = headerCell("Amount");
        lAmountHeader.setRowspan(2);
        labourTable.addCell(lAmountHeader);
        labourTable.addCell(headerCell("%"));
        labourTable.addCell(headerCell("Amt"));
        labourTable.addCell(headerCell("%"));
        labourTable.addCell(headerCell("Amt"));
        labourTable.addCell(headerCell("%"));
        labourTable.addCell(headerCell("Amt"));
        labourTable.addCell(headerCell("%"));
        labourTable.addCell(headerCell("Amt"));

        double labourSubTotal = 0.0;
        sNo = 1;
        for (LabourDto labour : request.getLabours()) {
            int qty = labour.getQuantity();
            double price = labour.getUnitPrice();
            double discPercent = labour.getDiscountPercent();
            double cgstRate = labour.getCgstPercent();
            double sgstRate = labour.getSgstPercent();
            double igstRate = labour.getIgstPercent();
            double totalPrice = price * qty;
            double discountAmt = totalPrice * (discPercent / 100.0);
            double taxableAmt = totalPrice - discountAmt;
            double cgstAmt = taxableAmt * (cgstRate / 100.0);
            double sgstAmt = taxableAmt * (sgstRate / 100.0);
            double igstAmt = taxableAmt * (igstRate / 100.0);
            double finalAmount = taxableAmt + cgstAmt + sgstAmt + igstAmt;
            labourSubTotal += finalAmount;
            labourTable.addCell(dataCell(String.valueOf(sNo++)));
            labourTable.addCell(dataCell(labour.getDescription()));
            labourTable.addCell(dataCell(String.valueOf(qty)));
            labourTable.addCell(dataCell(String.format("%.2f", price)));
            labourTable.addCell(dataCell(String.format("%.2f", discPercent)));
            labourTable.addCell(dataCell(String.format("%.2f", discountAmt)));
            labourTable.addCell(dataCell(String.format("%.2f", taxableAmt)));
            labourTable.addCell(dataCell(String.format("%.2f", cgstRate)));
            labourTable.addCell(dataCell(String.format("%.2f", cgstAmt)));
            labourTable.addCell(dataCell(String.format("%.2f", sgstRate)));
            labourTable.addCell(dataCell(String.format("%.2f", sgstAmt)));
            labourTable.addCell(dataCell(String.format("%.2f", igstRate)));
            labourTable.addCell(dataCell(String.format("%.2f", igstAmt)));
            labourTable.addCell(dataCell(String.format("%.2f", finalAmount)));
        }
        PdfPCell labourSubLabel = new PdfPCell(new Phrase("SUB TOTAL",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9)));
        labourSubLabel.setColspan(13);
        labourSubLabel.setHorizontalAlignment(Element.ALIGN_RIGHT);
        labourSubLabel.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
        labourTable.addCell(labourSubLabel);
        PdfPCell labourSubValue = new PdfPCell(new Phrase(String.format("%.2f", labourSubTotal),
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9)));
        labourSubValue.setHorizontalAlignment(Element.ALIGN_RIGHT);
        labourSubValue.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
        labourTable.addCell(labourSubValue);
        document.add(labourTable);

        // ---------------------------------------------------
        // (F) FINAL SUMMARY
        // ---------------------------------------------------
        double grandTotal = sparesSubTotal + labourSubTotal;
        double advAmount = request.getAdvanceAmount(); // Advance amount from request
        double netAmount = grandTotal; // Default: net = grandTotal

        PdfPTable finalSummary = new PdfPTable(14);
        finalSummary.setWidthPercentage(100f);
        finalSummary.setSpacingBefore(0f);
        finalSummary.setWidths(new float[]{
                4f, 30f, 4f, 8f, 6f, 8f, 10f, 6f, 8f, 6f, 8f, 6f, 8f, 10f
        });

        PdfPCell totalLabelCell = new PdfPCell(new Phrase("TOTAL AMOUNT",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9)));
        totalLabelCell.setColspan(13);
        totalLabelCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        totalLabelCell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
        finalSummary.addCell(totalLabelCell);
        PdfPCell totalValueCell = new PdfPCell(new Phrase(String.format("%.2f", grandTotal),
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9)));
        totalValueCell.setColspan(1);
        totalValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        totalValueCell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
        finalSummary.addCell(totalValueCell);

        // Row: ADVANCE AMOUNT
        PdfPCell advLabelCell = new PdfPCell(new Phrase("ADVANCE AMOUNT",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9)));
        advLabelCell.setColspan(13);
        advLabelCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        advLabelCell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
        finalSummary.addCell(advLabelCell);
        PdfPCell advValueCell = new PdfPCell(new Phrase(String.format("%.2f", advAmount),
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9)));
        advValueCell.setColspan(1);
        advValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        advValueCell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
        finalSummary.addCell(advValueCell);

        // Row: BALANCE DUE (only if advance > 0)
        if (advAmount > 0) {
            netAmount = grandTotal - advAmount;
            PdfPCell netLabelCell = new PdfPCell(new Phrase("BALANCE DUE",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9)));
            netLabelCell.setColspan(13);
            netLabelCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            netLabelCell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
            finalSummary.addCell(netLabelCell);
            PdfPCell netValueCell = new PdfPCell(new Phrase(String.format("%.2f", netAmount),
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9)));
            netValueCell.setColspan(1);
            netValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            netValueCell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
            finalSummary.addCell(netValueCell);
        }

        document.add(finalSummary);

        // ---------------------------------------------------
        // (G) TOTAL AMOUNT IN WORDS (WITH LINES + BOLD) - Using netAmount
        // ---------------------------------------------------
        if (netAmount < 0) {
            throw new IllegalArgumentException("Advance amount cannot exceed total amount.");
        }
        PdfPTable wordsTable = new PdfPTable(1);
        wordsTable.setWidthPercentage(100f);
        wordsTable.setSpacingBefore(0f);
        String wordsText = "Total Amount of Invoice in Words: Rs. "
                + convertNumberToWords((long) netAmount) + " only";
        Paragraph wordsPara = new Paragraph(wordsText,
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9));
        PdfPCell wordsCell = new PdfPCell(wordsPara);
        wordsCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
        wordsCell.setBorderWidthTop(1f);
        wordsCell.setBorderWidthBottom(1f);
        wordsCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        wordsCell.setPadding(5f);
        wordsTable.addCell(wordsCell);
        document.add(wordsTable);

        // ---------------------------------------------------
        // (H) COMMENTS / NOTES
        // ---------------------------------------------------
        PdfPTable noteTable = new PdfPTable(1);
        noteTable.setWidthPercentage(100f);
        noteTable.setSpacingBefore(0f);
        PdfPCell noteCell = new PdfPCell();
        noteCell.setBorder(Rectangle.NO_BORDER);
        noteCell.setPadding(5f);
        Paragraph noteParagraph = new Paragraph();
        noteParagraph.add(new Phrase("Customer Note:\n",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9)));
        noteParagraph.add(new Phrase("\n", FontFactory.getFont(FontFactory.HELVETICA, 8)));
        noteParagraph.add(new Phrase("Note:\n",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9)));
        if (request.getComments() != null && !request.getComments().isEmpty()) {
            noteParagraph.add(new Phrase("\n" + request.getComments(),
                    FontFactory.getFont(FontFactory.HELVETICA, 8)));
        }
        noteCell.addElement(noteParagraph);
        noteTable.addCell(noteCell);
        document.add(noteTable);

        // ---------------------------------------------------
        // (I) SIGNATURE / QR SECTION
        // ---------------------------------------------------
        PdfPTable signTable = new PdfPTable(3);
        signTable.setWidthPercentage(100f);
        signTable.setSpacingBefore(5f);
        signTable.setWidths(new float[]{30f, 35f, 35f});
        signTable.getDefaultCell().setFixedHeight(100f);
        PdfPCell qrCell = new PdfPCell();
        qrCell.setBorder(Rectangle.BOX);
        qrCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        qrCell.setVerticalAlignment(Element.ALIGN_TOP);
        qrCell.setFixedHeight(100f);
        Paragraph qrPlaceholder = new Paragraph("[QR CODE HERE]",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9));
        qrPlaceholder.setAlignment(Element.ALIGN_CENTER);
        qrCell.addElement(qrPlaceholder);
        Paragraph scanPayPara = new Paragraph("Scan To Pay",
                FontFactory.getFont(FontFactory.HELVETICA, 8));
        scanPayPara.setAlignment(Element.ALIGN_CENTER);
        qrCell.addElement(scanPayPara);
        signTable.addCell(qrCell);
        PdfPCell custSignCell = new PdfPCell();
        custSignCell.setBorder(Rectangle.BOX);
        custSignCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        custSignCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        custSignCell.setFixedHeight(100f);
        Paragraph custSignPara = new Paragraph("Customer Signature / Thumb",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9));
        custSignPara.setAlignment(Element.ALIGN_CENTER);
        custSignCell.addElement(custSignPara);
        signTable.addCell(custSignCell);
        PdfPCell rightSignCell = new PdfPCell();
        rightSignCell.setBorder(Rectangle.BOX);
        rightSignCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        rightSignCell.setVerticalAlignment(Element.ALIGN_TOP);
        rightSignCell.setFixedHeight(100f);
        PdfPTable nestedRight = new PdfPTable(1);
        nestedRight.setWidthPercentage(100f);
        nestedRight.setTotalWidth(rightSignCell.getWidth());
        PdfPCell topR = new PdfPCell(new Phrase("Auto Car Care Point",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9)));
        topR.setHorizontalAlignment(Element.ALIGN_CENTER);
        topR.setVerticalAlignment(Element.ALIGN_TOP);
        topR.setBorder(Rectangle.NO_BORDER);
        topR.setFixedHeight(50f);
        nestedRight.addCell(topR);
        PdfPCell bottomR = new PdfPCell(new Phrase("Authorized Signature",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9)));
        bottomR.setHorizontalAlignment(Element.ALIGN_CENTER);
        bottomR.setVerticalAlignment(Element.ALIGN_BOTTOM);
        bottomR.setBorder(Rectangle.NO_BORDER);
        bottomR.setFixedHeight(50f);
        nestedRight.addCell(bottomR);
        rightSignCell.addElement(nestedRight);
        signTable.addCell(rightSignCell);
        document.add(signTable);

        Paragraph thanksLine = new Paragraph(
                "Thank You For Visit.. This is a Computer Generated Invoice.",
                FontFactory.getFont(FontFactory.HELVETICA, 9));
        thanksLine.setAlignment(Element.ALIGN_CENTER);
        thanksLine.setSpacingBefore(5f);
        document.add(thanksLine);

        // ---------------------------------------------------
        // (J) DATE/TIME FOOTER + URL
        // ---------------------------------------------------
        String footerDate = new SimpleDateFormat("dd/MM/yyyy, HH:mm").format(new java.util.Date());
        Paragraph footerInfo = new Paragraph(footerDate + " Auto Car Care Point | Tax Invoice",
                FontFactory.getFont(FontFactory.HELVETICA, 8));
        footerInfo.setAlignment(Element.ALIGN_CENTER);
        footerInfo.setSpacingBefore(10f);
        document.add(footerInfo);
        Paragraph url = new Paragraph("Visit: autocarcares.com",
                FontFactory.getFont(FontFactory.HELVETICA, 8));
        url.setAlignment(Element.ALIGN_CENTER);
        document.add(url);

        // Close document and writer
        document.close();
        writer.close();

        return baos.toByteArray();
    }

    // -------------------------------------------------------------------------
    // HELPER: Table header cell
    // -------------------------------------------------------------------------
    private PdfPCell headerCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text,
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 7)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(3f);
        return cell;
    }
    // -------------------------------------------------------------------------
    // HELPER: Table data cell
    // -------------------------------------------------------------------------
    private PdfPCell dataCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text,
                FontFactory.getFont(FontFactory.HELVETICA, 7)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(3f);
        return cell;
    }
    // -------------------------------------------------------------------------
    // PAGE BORDER EVENT
    // -------------------------------------------------------------------------
    private class BorderEvent extends PdfPageEventHelper {
        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte canvas = writer.getDirectContent();
            canvas.setLineWidth(1);
            canvas.setColorStroke(BaseColor.BLACK);
            float llx = document.left();
            float lly = document.bottom();
            float urx = document.right();
            float ury = document.top();
            canvas.rectangle(llx, lly, (urx - llx), (ury - lly));
            canvas.stroke();
        }
    }
    // -------------------------------------------------------------------------
    // NUMBER TO WORDS
    // -------------------------------------------------------------------------
    private String convertNumberToWords(long number) {
        if (number == 0) return "Zero";

        String[] tensNames = {
                "", " Ten", " Twenty", " Thirty", " Forty",
                " Fifty", " Sixty", " Seventy", " Eighty", " Ninety"
        };
        String[] numNames = {
                "", " One", " Two", " Three", " Four", " Five", " Six",
                " Seven", " Eight", " Nine", " Ten", " Eleven", " Twelve",
                " Thirteen", " Fourteen", " Fifteen", " Sixteen", " Seventeen",
                " Eighteen", " Nineteen"
        };

        String words;
        if (number < 20) {
            words = numNames[(int) number];
        } else if (number < 100) {
            words = tensNames[(int) (number / 10)] + ((number % 10 != 0) ? numNames[(int) (number % 10)] : "");
        } else if (number < 1000) {
            words = numNames[(int) (number / 100)] + " Hundred" +
                    ((number % 100 != 0) ? " and " + convertNumberToWords(number % 100) : "");
        } else if (number < 100000) {
            words = convertNumberToWords(number / 1000) + " Thousand" +
                    ((number % 1000 != 0) ? " " + convertNumberToWords(number % 1000) : "");
        } else if (number < 10000000) {
            words = convertNumberToWords(number / 100000) + " Lakh" +
                    ((number % 100000 != 0) ? " " + convertNumberToWords(number % 100000) : "");
        } else {
            words = convertNumberToWords(number / 10000000) + " Crore" +
                    ((number % 10000000 != 0) ? " " + convertNumberToWords(number % 10000000) : "");
        }
        return words.trim();
    }
}
