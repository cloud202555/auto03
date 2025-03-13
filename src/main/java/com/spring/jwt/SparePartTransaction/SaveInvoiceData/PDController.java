//package com.spring.jwt.SparePartTransaction.SaveInvoiceData;
//
//import com.spring.jwt.SparePartTransaction.SaveInvoiceData.Repository.InvoiceRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/invoices")
//public class PDController {
//    private final InvoiceRepository invoiceRepository;
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Invoice> getInvoice(@PathVariable Long id) {
//        return invoiceRepository.findById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//}
