package com.spring.jwt.SparePartTransaction.CounterSale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private InvoiceRepository invoiceRepository; // To generate invoice number based on count

    @PostMapping("AddInvoice")
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) {

        long count = invoiceRepository.count();
        long nextNum = 1000 + count;
        String invoiceNumber = String.format("%07d", nextNum);
        invoice.setInvoiceNumber(invoiceNumber);

        Invoice savedInvoice = invoiceService.saveInvoice(invoice);
        return ResponseEntity.ok(savedInvoice);
    }

    @GetMapping("getAll")
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }
}
