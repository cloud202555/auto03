package com.spring.jwt.SparePartTransaction.Pdf;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/pdf")
@RequiredArgsConstructor
public class PdfGenerateController {

    private final PdfGenerationService pdfGenerationService;

    /**
     * Endpoint to generate the invoice PDF.
     *
     * Required request parameters:
     * - vehicleRegId: ID to fetch VehicleReg details.
     * - quantity: Quantity for the spare part.
     * - discountPercent: Discount percentage for spares.
     * - cGst: CGST percentage.
     * - sGst: SGST percentage.
     * - invoiceNumber: Invoice number.
     * - jobcardNo: Jobcard number.
     * - jobcardDate: Jobcard date (dd-MM-yyyy).
     * - kmsDriven: Kilometers driven.
     *
     * Additional labour work parameters (provided from front end):
     * - labourParticulars: Description for the labour work.
     * - labourQty: Quantity for labour work.
     * - labourUnitPrice: Unit price for labour work.
     * - labourDiscountPercent: Discount percentage for labour work.
     *
     * Optional parameters:
     * - slogan: Company slogan.
     * - comments: Additional comments.
     *
     * @return The generated PDF as an attachment.
     */
    @GetMapping("/generate")
    public ResponseEntity<byte[]> generatePdf(
            @RequestParam Integer vehicleRegId,
            @RequestParam String quantity,
            @RequestParam String discountPercent,
            @RequestParam String cGst,
            @RequestParam String sGst,
            @RequestParam String invoiceNumber,
            @RequestParam String jobcardNo,
            @RequestParam String jobcardDate,
            @RequestParam String kmsDriven,
            @RequestParam(required = false) String slogan,
            @RequestParam(required = false) String comments,
            // New labour work parameters from front end:
            @RequestParam String labourParticulars,
            @RequestParam String labourQty,
            @RequestParam String labourUnitPrice,
            @RequestParam String labourDiscountPercent
    ) {
        try {
            byte[] pdfData = pdfGenerationService.generatePdf(
                    vehicleRegId,
                    quantity,
                    discountPercent,
                    cGst,
                    sGst,
                    invoiceNumber,
                    jobcardNo,
                    jobcardDate,
                    kmsDriven,
                    slogan,
                    comments,
                    labourParticulars,
                    labourQty,
                    labourUnitPrice,
                    labourDiscountPercent
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "invoice.pdf");
            headers.setContentLength(pdfData.length);
            return ResponseEntity.ok().headers(headers).body(pdfData);
        } catch (RuntimeException e) {
            byte[] errorResponse = serializeResponse(e.getMessage());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setContentLength(errorResponse.length);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(errorResponse);
        } catch (Exception e) {
            byte[] errorResponse = serializeResponse("Internal Server Error");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setContentLength(errorResponse.length);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body(errorResponse);
        }
    }

    private byte[] serializeResponse(Object message) {
        try {
            return new ObjectMapper().writeValueAsBytes(Collections.singletonMap("error", message));
        } catch (JsonProcessingException ex) {
            return "{\"error\": \"An error occurred\"}".getBytes();
        }
    }
}
