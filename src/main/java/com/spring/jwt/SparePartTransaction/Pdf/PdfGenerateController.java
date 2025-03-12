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
     * Endpoint to generate the invoice PDF from a JSON body.
     * The JSON should match the structure of PdfRequest, e.g.:
     *
     * {
     *   "vehicleRegId": 123,
     *   "invoiceNumber": "INV-001",
     *   "jobcardNo": "JC-456",
     *   "jobcardDate": "01-12-2023",
     *   "kmsDriven": "35000",
     *   "slogan": "Quality Service",
     *   "comments": "Check brake pads also",
     *   "parts": [
     *     {
     *       "partName": "Clutch Set",
     *       "quantity": 1,
     *       "unitPrice": 2736.00,
     *       "discountPercent": 5.0,
     *       "cgstPercent": 9.0,
     *       "sgstPercent": 9.0,
     *       "igstPercent": 0.0
     *     }
     *   ],
     *   "labours": [
     *     {
     *       "description": "Gear Box Repair",
     *       "quantity": 1,
     *       "unitPrice": 300.00,
     *       "discountPercent": 0.0,
     *       "cgstPercent": 9.0,
     *       "sgstPercent": 9.0,
     *       "igstPercent": 0.0
     *     }
     *   ]
     * }
     */
    @PostMapping("/generate")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<byte[]> generatePdf(@RequestBody PdfRequest request) {
        try {
            byte[] pdfData = pdfGenerationService.generatePdf(request);

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
