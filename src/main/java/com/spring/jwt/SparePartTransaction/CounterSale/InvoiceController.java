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
    private InvoiceRepository invoiceRepository;

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

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id) {
        Invoice invoice = invoiceService.getInvoiceById(id);
        if (invoice != null) {
            return ResponseEntity.ok(invoice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/dateRange")
    public ResponseEntity<List<Invoice>> getInvoicesByDateRange(
            @RequestParam("from") String fromDate,
            @RequestParam("to") String toDate) {
        List<Invoice> invoices = invoiceService.getInvoicesByDateRange(fromDate, toDate);
        return ResponseEntity.ok(invoices);
    }
}
