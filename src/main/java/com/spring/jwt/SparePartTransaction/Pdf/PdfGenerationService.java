package com.spring.jwt.SparePartTransaction.Pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.spring.jwt.SparePartTransaction.SparePartTransaction;
import com.spring.jwt.SparePartTransaction.SparePartTransactionRepository;
import com.spring.jwt.VehicleReg.VehicleRegRepository;
import com.spring.jwt.entity.VehicleReg;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PdfGenerationService {

    private final VehicleRegRepository vehicleRegRepository;
    private final SparePartTransactionRepository sparePartRepository;

    public byte[] generatePdf(Integer vehicleRegId,
                              String quantityParam,
                              String discountPercentParam,
                              String cGstParam,
                              String sGstParam,
                              String invoiceNumber,
                              String jobcardNo,
                              String jobcardDate, // dd-MM-yyyy
                              String kmsDriven,
                              String slogan,
                              String comments,
                              // Labour params from front end
                              String labourParticulars,
                              String labourQtyParam,
                              String labourUnitPriceParam,
                              String labourDiscountPercentParam) throws Exception {

        // 1) Fetch VehicleReg
        VehicleReg vehicle = vehicleRegRepository.findById(vehicleRegId)
                .orElseThrow(() -> new RuntimeException("VehicleReg not found with ID: " + vehicleRegId));

        // 2) Fetch SparePartTransactions
        List<SparePartTransaction> transactions = sparePartRepository.findByVehicleRegId(vehicleRegId);
        if (transactions == null || transactions.isEmpty()) {
            throw new RuntimeException("No SparePartTransaction found for VehicleRegId: " + vehicleRegId);
        }

        // 3) Parse numeric params
        int defaultQuantity        = Integer.parseInt(quantityParam);
        double defaultDiscount     = Double.parseDouble(discountPercentParam);
        double cgstRate            = Double.parseDouble(cGstParam);
        double sgstRate            = Double.parseDouble(sGstParam);

        // Parse labour params
        int labourQty             = Integer.parseInt(labourQtyParam);
        double labourUnitPrice    = Double.parseDouble(labourUnitPriceParam);
        double labourDiscount     = Double.parseDouble(labourDiscountPercentParam);

        // 4) Invoice date
        LocalDate invDate = (vehicle.getDate() != null) ? vehicle.getDate() : LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String invoiceDate = invDate.format(dtf);

        // 5) Prepare PDF
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 20, 20, 20, 20);
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        writer.setPageEvent(new BorderEvent()); // Page border
        document.open();

        // ---------------------------------------------------------------------
        // (A) TOP LINE: date/time on left, "Auto Car Care Point | Tax Invoice" on right
        // ---------------------------------------------------------------------
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

        // ---------------------------------------------------------------------
        // (B) BIG BORDERED TABLE: left = company info, right = "TAX INVOICE"
        // ---------------------------------------------------------------------
        PdfPTable headerBox = new PdfPTable(2);
        headerBox.setWidthPercentage(100f);
        headerBox.setWidths(new float[]{70f, 30f});

        PdfPCell headerLeft = new PdfPCell();
        headerLeft.setBorder(Rectangle.BOX);
        headerLeft.setPadding(5f);

        Paragraph compName = new Paragraph("AUTO CAR CARE POINT",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11));
        Paragraph compAddr = new Paragraph(
                "Buvasaheb Nagar, Shingnapur Road, Kolki, Tal.Phaltan(415523), Dist.Satara.\n" +
                        "Ph : 9595054555 / 7758817766   Email : autocarcarepoint@gmail.com\n" +
                        "GSTIN : 27GLYPS9891C1ZV",
                FontFactory.getFont(FontFactory.HELVETICA, 9));
        Paragraph compSlogan = new Paragraph(
                (slogan != null && !slogan.isEmpty()) ? slogan : "Quality Service",
                FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 9));

        headerLeft.addElement(compName);
        headerLeft.addElement(compAddr);
        headerLeft.addElement(compSlogan);
        headerBox.addCell(headerLeft);

        PdfPCell headerRight = new PdfPCell();
        headerRight.setBorder(Rectangle.BOX);
        headerRight.setPadding(5f);
        headerRight.setHorizontalAlignment(Element.ALIGN_RIGHT);

        Paragraph taxInvoice = new Paragraph("TAX INVOICE",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
        headerRight.addElement(taxInvoice);

        headerBox.addCell(headerRight);
        document.add(headerBox);

        // ---------------------------------------------------------------------
        // (C) CUSTOMER & VEHICLE DETAILS - side by side boxes
        // ---------------------------------------------------------------------
        PdfPTable custVehTable = new PdfPTable(2);
        custVehTable.setWidthPercentage(100);
        custVehTable.setWidths(new float[]{50f, 50f});
        custVehTable.setSpacingBefore(10f);

        // CUSTOMER DETAILS
        PdfPCell custBox = new PdfPCell();
        custBox.setBorder(Rectangle.BOX);
        custBox.setPadding(5f);

        Paragraph custTitle = new Paragraph("CUSTOMER DETAILS",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10));
        custTitle.setAlignment(Element.ALIGN_CENTER);
        custBox.addElement(custTitle);

        custBox.addElement(new Paragraph("Name : " + vehicle.getCustomerName(),
                FontFactory.getFont(FontFactory.HELVETICA, 9)));
        custBox.addElement(new Paragraph("Address : " + vehicle.getCustomerAddress(),
                FontFactory.getFont(FontFactory.HELVETICA, 9)));
        custBox.addElement(new Paragraph("Mobile : " + vehicle.getCustomerMobileNumber(),
                FontFactory.getFont(FontFactory.HELVETICA, 9)));
        custBox.addElement(new Paragraph("Email : No",
                FontFactory.getFont(FontFactory.HELVETICA, 9)));
        custBox.addElement(new Paragraph("Aadhar No : " + vehicle.getCustomerAadharNo(),
                FontFactory.getFont(FontFactory.HELVETICA, 9)));
        custBox.addElement(new Paragraph("GSTIN : " + vehicle.getCustomerGstin(),
                FontFactory.getFont(FontFactory.HELVETICA, 9)));

        custVehTable.addCell(custBox);

        // VEHICLE DETAILS
        PdfPCell vehBox = new PdfPCell();
        vehBox.setBorder(Rectangle.BOX);
        vehBox.setPadding(5f);

        Paragraph vehTitle = new Paragraph("VEHICLE DETAILS",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10));
        vehTitle.setAlignment(Element.ALIGN_CENTER);
        vehBox.addElement(vehTitle);

        vehBox.addElement(new Paragraph("Invoice No : " + invoiceNumber,
                FontFactory.getFont(FontFactory.HELVETICA, 9)));
        vehBox.addElement(new Paragraph("Invoice Date : " + invoiceDate,
                FontFactory.getFont(FontFactory.HELVETICA, 9)));
        vehBox.addElement(new Paragraph("Reg. No : " + vehicle.getVehicleNumber(),
                FontFactory.getFont(FontFactory.HELVETICA, 9)));
        vehBox.addElement(new Paragraph("Model : " + vehicle.getVehicleBrand()
                + " - " + vehicle.getVehicleModelName(),
                FontFactory.getFont(FontFactory.HELVETICA, 9)));
        vehBox.addElement(new Paragraph("Jobcard No : " + jobcardNo,
                FontFactory.getFont(FontFactory.HELVETICA, 9)));
        vehBox.addElement(new Paragraph("Jobcard Date : " + jobcardDate,
                FontFactory.getFont(FontFactory.HELVETICA, 9)));
        vehBox.addElement(new Paragraph("KMS. Driven : " + kmsDriven,
                FontFactory.getFont(FontFactory.HELVETICA, 9)));
        vehBox.addElement(new Paragraph("Engine No : " + vehicle.getEngineNumber(),
                FontFactory.getFont(FontFactory.HELVETICA, 9)));

        custVehTable.addCell(vehBox);

        document.add(custVehTable);
        document.add(Chunk.NEWLINE);

        // ---------------------------------------------------------------------
        // (D) SPARES TABLE
        // ---------------------------------------------------------------------
        Paragraph sparesHeading = new Paragraph("SPARES",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
        sparesHeading.setSpacingAfter(2f);
        document.add(sparesHeading);

        PdfPTable sparesTable = new PdfPTable(14);
        sparesTable.setWidthPercentage(100);
        sparesTable.setSpacingBefore(2f);
        sparesTable.setWidths(new float[]{
                4f, 30f, 4f, 8f, 6f, 8f, 10f, 6f, 8f, 6f, 8f, 6f, 8f, 10f
        });

        sparesTable.addCell(headerCell("S.No"));
        sparesTable.addCell(headerCell("Perticulars Of Parts"));
        sparesTable.addCell(headerCell("Qty"));
        sparesTable.addCell(headerCell("Unit Price"));
        sparesTable.addCell(headerCell("Discount (%)"));
        sparesTable.addCell(headerCell("Discount Amt"));
        sparesTable.addCell(headerCell("Taxable Amt"));
        sparesTable.addCell(headerCell("CGST %"));
        sparesTable.addCell(headerCell("Amt"));
        sparesTable.addCell(headerCell("SGST %"));
        sparesTable.addCell(headerCell("Amt"));
        sparesTable.addCell(headerCell("IGST %"));
        sparesTable.addCell(headerCell("Amt"));
        sparesTable.addCell(headerCell("Amount"));

        int serial = 1;
        double sparesSubTotal = 0.0;
        for (SparePartTransaction txn : transactions) {
            int qty = (txn.getQuantity() != null) ? txn.getQuantity() : defaultQuantity;
            double discPercent = defaultDiscount;
            double price = (txn.getPrice() != null) ? txn.getPrice().doubleValue() : 0.0;

            double totalPrice   = price * qty;
            double discountAmt  = totalPrice * (discPercent / 100.0);
            double taxableAmt   = totalPrice - discountAmt;
            double cgstAmt      = taxableAmt * (cgstRate / 100.0);
            double sgstAmt      = taxableAmt * (sgstRate / 100.0);
            double igstAmt      = 0.0;
            double finalAmount  = taxableAmt + cgstAmt + sgstAmt + igstAmt;

            sparesSubTotal     += finalAmount;

            sparesTable.addCell(dataCell(String.valueOf(serial++)));
            sparesTable.addCell(dataCell(txn.getPartName()));
            sparesTable.addCell(dataCell(String.valueOf(qty)));
            sparesTable.addCell(dataCell(String.format("%.2f", price)));
            sparesTable.addCell(dataCell(String.format("%.2f", discPercent)));
            sparesTable.addCell(dataCell(String.format("%.2f", discountAmt)));
            sparesTable.addCell(dataCell(String.format("%.2f", taxableAmt)));
            sparesTable.addCell(dataCell(String.format("%.2f", cgstRate)));
            sparesTable.addCell(dataCell(String.format("%.2f", cgstAmt)));
            sparesTable.addCell(dataCell(String.format("%.2f", sgstRate)));
            sparesTable.addCell(dataCell(String.format("%.2f", sgstAmt)));
            sparesTable.addCell(dataCell(String.format("%.2f", 0.0))); // IGST %
            sparesTable.addCell(dataCell(String.format("%.2f", igstAmt)));
            sparesTable.addCell(dataCell(String.format("%.2f", finalAmount)));
        }

        // SUB TOTAL inside SPARES
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

        // ---------------------------------------------------------------------
        // (E) LABOUR WORK TABLE
        // ---------------------------------------------------------------------
        Paragraph labourHeading = new Paragraph("LABOUR WORK",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
        labourHeading.setSpacingAfter(2f);
        document.add(labourHeading);

        PdfPTable labourTable = new PdfPTable(14);
        labourTable.setWidthPercentage(100);
        labourTable.setSpacingBefore(2f);
        labourTable.setWidths(new float[]{
                4f, 30f, 4f, 8f, 6f, 8f, 10f, 6f, 8f, 6f, 8f, 6f, 8f, 10f
        });

        labourTable.addCell(headerCell("S.No"));
        labourTable.addCell(headerCell("Perticulars Of Services"));
        labourTable.addCell(headerCell("Qty"));
        labourTable.addCell(headerCell("Unit Price"));
        labourTable.addCell(headerCell("Discount (%)"));
        labourTable.addCell(headerCell("Discount Amt"));
        labourTable.addCell(headerCell("Taxable Amt"));
        labourTable.addCell(headerCell("CGST %"));
        labourTable.addCell(headerCell("Amt"));
        labourTable.addCell(headerCell("SGST %"));
        labourTable.addCell(headerCell("Amt"));
        labourTable.addCell(headerCell("IGST %"));
        labourTable.addCell(headerCell("Amt"));
        labourTable.addCell(headerCell("Amount"));

        double labourTotalPrice = labourUnitPrice * labourQty;
        double labourDiscountAmt = labourTotalPrice * (labourDiscount / 100.0);
        double labourTaxableAmt  = labourTotalPrice - labourDiscountAmt;
        double labourCgstAmt     = labourTaxableAmt * (cgstRate / 100.0);
        double labourSgstAmt     = labourTaxableAmt * (sgstRate / 100.0);
        double labourIgstAmt     = 0.0;
        double labourFinalAmt    = labourTaxableAmt + labourCgstAmt + labourSgstAmt + labourIgstAmt;

        labourTable.addCell(dataCell("1"));
        labourTable.addCell(dataCell(labourParticulars));
        labourTable.addCell(dataCell(String.valueOf(labourQty)));
        labourTable.addCell(dataCell(String.format("%.2f", labourUnitPrice)));
        labourTable.addCell(dataCell(String.format("%.2f", labourDiscount)));
        labourTable.addCell(dataCell(String.format("%.2f", labourDiscountAmt)));
        labourTable.addCell(dataCell(String.format("%.2f", labourTaxableAmt)));
        labourTable.addCell(dataCell(String.format("%.2f", cgstRate)));
        labourTable.addCell(dataCell(String.format("%.2f", labourCgstAmt)));
        labourTable.addCell(dataCell(String.format("%.2f", sgstRate)));
        labourTable.addCell(dataCell(String.format("%.2f", labourSgstAmt)));
        labourTable.addCell(dataCell(String.format("%.2f", 0.0))); // IGST %
        labourTable.addCell(dataCell(String.format("%.2f", labourIgstAmt)));
        labourTable.addCell(dataCell(String.format("%.2f", labourFinalAmt)));

        // SUB TOTAL inside LABOUR
        PdfPCell labourSubLabel = new PdfPCell(new Phrase("SUB TOTAL",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9)));
        labourSubLabel.setColspan(13);
        labourSubLabel.setHorizontalAlignment(Element.ALIGN_RIGHT);
        labourSubLabel.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
        labourTable.addCell(labourSubLabel);

        PdfPCell labourSubValue = new PdfPCell(new Phrase(String.format("%.2f", labourFinalAmt),
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9)));
        labourSubValue.setHorizontalAlignment(Element.ALIGN_RIGHT);
        labourSubValue.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
        labourTable.addCell(labourSubValue);

        document.add(labourTable);

        // ---------------------------------------------------------------------
        // (F) FINAL SUMMARY (14 columns) - one horizontal line between TOTAL and ADVANCE
        // ---------------------------------------------------------------------
        double grandTotal = sparesSubTotal + labourFinalAmt;

        PdfPTable finalSummary = new PdfPTable(14);
        finalSummary.setWidthPercentage(100f);
        finalSummary.setSpacingBefore(0f);
        finalSummary.setWidths(new float[]{
                4f, 30f, 4f, 8f, 6f, 8f, 10f, 6f, 8f, 6f, 8f, 6f, 8f, 10f
        });

        // 1) TOTAL AMOUNT row
        PdfPCell totalLabelCell = new PdfPCell(new Phrase("TOTAL AMOUNT",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9)));
        totalLabelCell.setColspan(13);
        totalLabelCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        // continuing line from labour sub total
        totalLabelCell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
        finalSummary.addCell(totalLabelCell);

        PdfPCell totalValueCell = new PdfPCell(new Phrase(String.format("%.2f", grandTotal),
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9)));
        totalValueCell.setColspan(1);
        totalValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        totalValueCell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
        finalSummary.addCell(totalValueCell);

        // 2) ADVANCE AMOUNT row: top border => single horizontal line between total & adv
        PdfPCell advLabelCell = new PdfPCell(new Phrase("ADVANCE AMOUNT",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9)));
        advLabelCell.setColspan(13);
        advLabelCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        // top => single line
        advLabelCell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
        finalSummary.addCell(advLabelCell);

        PdfPCell advValueCell = new PdfPCell(new Phrase(String.format("%.2f", 0.0),
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9)));
        advValueCell.setColspan(1);
        advValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        advValueCell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
        finalSummary.addCell(advValueCell);

        document.add(finalSummary);

        // Single, full-width line for the “Amount in Words,” with a box border
        PdfPTable wordsTable = new PdfPTable(1);
        wordsTable.setWidthPercentage(100f);
        wordsTable.setSpacingBefore(3f);

        String wordsText = "Total Amount of Invoice in Words: Rs. "
                + convertNumberToWords((long) grandTotal) + " only";
        PdfPCell wordsCell = new PdfPCell(new Phrase(wordsText,
                FontFactory.getFont(FontFactory.HELVETICA, 9)));
        wordsCell.setBorder(Rectangle.BOX);
        wordsCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        wordsCell.setPadding(5f);
        wordsTable.addCell(wordsCell);

        document.add(wordsTable);
        document.add(Chunk.NEWLINE);

        // ---------------------------------------------------------------------
        // (H) BOTTOM SECTION:
        // ---------------------------------------------------------------------
        // 1) Single big cell for "Customer Note" & "Note"
        PdfPTable noteTable = new PdfPTable(1);
        noteTable.setWidthPercentage(100f);
        noteTable.setSpacingBefore(5f);

        PdfPCell noteCell = new PdfPCell();
        noteCell.setBorder(Rectangle.BOX);
        noteCell.setPadding(5f);

        Paragraph noteParagraph = new Paragraph();
        noteParagraph.add(new Phrase("Customer Note:\n",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9)));
        noteParagraph.add(new Phrase("\n", FontFactory.getFont(FontFactory.HELVETICA, 8))); // blank line
        noteParagraph.add(new Phrase("Note:\n",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9)));

        if (comments != null && !comments.isEmpty()) {
            noteParagraph.add(new Phrase("\n" + comments,
                    FontFactory.getFont(FontFactory.HELVETICA, 8)));
        }

        noteCell.addElement(noteParagraph);
        noteTable.addCell(noteCell);
        document.add(noteTable);

        // 2) Three-column row: left=QR code, middle=Customer Signature, right=Auto Car + Authorized
        PdfPTable signTable = new PdfPTable(3);
        signTable.setWidthPercentage(100f);
        signTable.setSpacingBefore(5f);
        // Adjust these widths to increase/decrease the column sizes
        signTable.setWidths(new float[]{30f, 35f, 35f});
        // Increase row height
        signTable.getDefaultCell().setFixedHeight(100f);

        // LEFT column: QR code + "Scan To Pay"
        PdfPCell qrCell = new PdfPCell();
        qrCell.setBorder(Rectangle.BOX);
        qrCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        qrCell.setVerticalAlignment(Element.ALIGN_TOP); // place content at top
        qrCell.setFixedHeight(100f); // Increase to desired size

        // If you have an actual QR image, you can add it here
        // For now, just a placeholder
        Paragraph qrPlaceholder = new Paragraph("[QR CODE HERE]",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9));
        qrPlaceholder.setAlignment(Element.ALIGN_CENTER);
        qrCell.addElement(qrPlaceholder);

        Paragraph scanPayPara = new Paragraph("Scan To Pay",
                FontFactory.getFont(FontFactory.HELVETICA, 8));
        scanPayPara.setAlignment(Element.ALIGN_CENTER);
        qrCell.addElement(scanPayPara);

        signTable.addCell(qrCell);

        // MIDDLE column: "Customer Signature / Thumb" at vertical center or top/bottom as needed
        PdfPCell custSignCell = new PdfPCell();
        custSignCell.setBorder(Rectangle.BOX);
        custSignCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        custSignCell.setVerticalAlignment(Element.ALIGN_MIDDLE); // center
        custSignCell.setFixedHeight(100f);

        Paragraph custSignPara = new Paragraph("Customer Signature / Thumb",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9));
        custSignPara.setAlignment(Element.ALIGN_CENTER);
        custSignCell.addElement(custSignPara);

        signTable.addCell(custSignCell);

        // RIGHT column: "Auto Car Care Point" at top, "Authorized Signature" at bottom
        PdfPCell rightSignCell = new PdfPCell();
        rightSignCell.setBorder(Rectangle.BOX);
        rightSignCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        rightSignCell.setVerticalAlignment(Element.ALIGN_TOP); // we'll do nested approach
        rightSignCell.setFixedHeight(100f);

        // We use a nested table with 2 rows: top row = "Auto Car Care Point", bottom row = "Authorized Signature"
        PdfPTable nestedRight = new PdfPTable(1);
        nestedRight.setWidthPercentage(100f);
        nestedRight.setTotalWidth(rightSignCell.getWidth());
        // The top row
        PdfPCell topR = new PdfPCell(new Phrase("Auto Car Care Point",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9)));
        topR.setHorizontalAlignment(Element.ALIGN_CENTER);
        topR.setVerticalAlignment(Element.ALIGN_TOP);
        topR.setBorder(Rectangle.NO_BORDER);
        topR.setFixedHeight(50f); // Adjust to push content to top
        nestedRight.addCell(topR);

        // The bottom row
        PdfPCell bottomR = new PdfPCell(new Phrase("Authorized Signature",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9)));
        bottomR.setHorizontalAlignment(Element.ALIGN_CENTER);
        bottomR.setVerticalAlignment(Element.ALIGN_BOTTOM);
        bottomR.setBorder(Rectangle.NO_BORDER);
        bottomR.setFixedHeight(50f); // Adjust to push content to bottom
        nestedRight.addCell(bottomR);

        rightSignCell.addElement(nestedRight);
        signTable.addCell(rightSignCell);

        document.add(signTable);

        // 3) Final line: "Thank You For Visit.."
        Paragraph thanksLine = new Paragraph(
                "Thank You For Visit.. This is a Computer Generated Invoice | Software Developed by Regex Technologies.",
                FontFactory.getFont(FontFactory.HELVETICA, 9));
        thanksLine.setAlignment(Element.ALIGN_CENTER);
        thanksLine.setSpacingBefore(5f);
        document.add(thanksLine);

        // ---------------------------------------------------------------------
        // (I) DATE/TIME FOOTER + URL
        // ---------------------------------------------------------------------
        String footerDate = new SimpleDateFormat("dd/MM/yyyy, HH:mm").format(new java.util.Date());
        Paragraph footerInfo = new Paragraph(footerDate + " Auto Car Care Point | Tax Invoice",
                FontFactory.getFont(FontFactory.HELVETICA, 8));
        footerInfo.setAlignment(Element.ALIGN_CENTER);
        footerInfo.setSpacingBefore(10f);
        document.add(footerInfo);

        Paragraph url = new Paragraph("https://autocarcarepoint.com/job?action=printInvoice&veh_reg_id=" + vehicleRegId + " 1/1",
                FontFactory.getFont(FontFactory.HELVETICA, 8));
        url.setAlignment(Element.ALIGN_CENTER);
        document.add(url);

        // Close
        document.close();
        writer.close();

        return baos.toByteArray();
    }

    // -------------------------------------------------------------------------
    // Helper Methods for Table Cells
    // -------------------------------------------------------------------------
    private PdfPCell headerCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text,
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 7)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(3f);
        return cell;
    }

    private PdfPCell dataCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text,
                FontFactory.getFont(FontFactory.HELVETICA, 7)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(3f);
        return cell;
    }

    // -------------------------------------------------------------------------
    // Page Border Event
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
    // Number to Words
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
            words = tensNames[(int) (number / 10)]
                    + ((number % 10 != 0) ? numNames[(int) (number % 10)] : "");
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
