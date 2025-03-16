package com.spring.jwt.SparePartTransaction;

import java.time.LocalDateTime;
import java.util.List;

public interface SparePartTransactionService {

    SparePartTransactionDto createTransaction(CreateSparePartTransactionDto transactionDto);

    SparePartTransactionDto getTransactionById(Integer transactionId);

    List<SparePartTransactionDto> getAllTransactions();

    SparePartTransactionDto updateTransaction(Integer transactionId, SparePartTransactionDto transactionDto);

    void deleteTransaction(Integer transactionId);

    List<SparePartTransactionDto> getByBillNo(String billNo);

    List<SparePartTransactionDto> getByUserId (Integer userId);

    public List<SparePartTransactionDto> getByVehicleRegId(Integer vehicleRegId);

    List<SparePartTransactionDto> getTransactionsBetweenDates(LocalDateTime startDate, LocalDateTime endDate);

    List<SparePartTransactionDto> getByPartNumberAndTransactionsBetweenDates(Integer sparePartId, LocalDateTime startDate, LocalDateTime endDate);


    List<SparePartTransactionDto> getByTransactionTypeAndNameAndDateRange(TransactionType transactionType, String name, LocalDateTime startDate, LocalDateTime endDate);

    List<SparePartTransactionDto> getByNameOrPartNumber(String name, String partNumber);

}
