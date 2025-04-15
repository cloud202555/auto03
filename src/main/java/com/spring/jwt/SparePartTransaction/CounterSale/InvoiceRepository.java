package com.spring.jwt.SparePartTransaction.CounterSale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findByInvDateBetween(String fromDate, String toDate);
}
