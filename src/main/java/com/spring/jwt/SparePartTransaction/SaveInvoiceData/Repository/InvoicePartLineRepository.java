package com.spring.jwt.SparePartTransaction.SaveInvoiceData.Repository;

import com.spring.jwt.SparePartTransaction.SaveInvoiceData.QuotationPartLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoicePartLineRepository extends JpaRepository<QuotationPartLine, Long> {
}