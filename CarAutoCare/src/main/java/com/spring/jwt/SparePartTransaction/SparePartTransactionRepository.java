package com.spring.jwt.SparePartTransaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SparePartTransactionRepository extends JpaRepository<SparePartTransaction, Integer> {
    List<SparePartTransaction> findByUserId(Integer userId);

    List<SparePartTransaction> findByTransactionDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<SparePartTransaction> findByBillNo(String billNo);
}
