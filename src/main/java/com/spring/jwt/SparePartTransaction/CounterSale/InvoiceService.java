package com.spring.jwt.SparePartTransaction.CounterSale;

import java.util.List;

public interface InvoiceService {
    Invoice saveInvoice(Invoice invoice);
    List<Invoice> getAllInvoices();
}

