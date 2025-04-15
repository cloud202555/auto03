package com.spring.jwt.SparePartTransaction.CounterSale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public Invoice saveInvoice(Invoice invoice) {
        if (invoice.getItems() != null) {
            invoice.getItems().forEach(item -> {
                item.setId(null);
                item.setInvoice(invoice);
            });
        }
        return invoiceRepository.save(invoice);
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public Invoice getInvoiceById(Long id) {
        Optional<Invoice> invoiceOpt = invoiceRepository.findById(id);
        return invoiceOpt.orElse(null);
    }
    public List<Invoice> getInvoicesByDateRange(String fromDate, String toDate) {
        return invoiceRepository.findByInvDateBetween(fromDate, toDate);
    }
}
