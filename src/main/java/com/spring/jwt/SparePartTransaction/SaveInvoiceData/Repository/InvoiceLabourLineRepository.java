package com.spring.jwt.SparePartTransaction.SaveInvoiceData.Repository;

import com.spring.jwt.SparePartTransaction.SaveInvoiceData.QuotationlabourLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceLabourLineRepository extends JpaRepository<QuotationlabourLine, Long> {
}