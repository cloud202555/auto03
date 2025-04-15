package com.spring.jwt.SparePartTransaction.SaveInvoiceData.Repository;

import com.spring.jwt.SparePartTransaction.SaveInvoiceData.Quotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuotationRepository extends JpaRepository<Quotation, Long> {
}