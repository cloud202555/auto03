package com.spring.jwt.SparePartTransaction;

import com.spring.jwt.SparePart.SparePart;
import com.spring.jwt.SparePart.SparePartRepo;
import com.spring.jwt.UserParts.UserPart;
import com.spring.jwt.UserParts.UserPartRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SparePartTransactionServiceImpl implements SparePartTransactionService {

    @Autowired
    private SparePartTransactionRepository transactionRepository;

    @Autowired
    private SparePartRepo sparePartRepository;  // Assuming you have a SparePart entity & repository

    @Autowired
    private UserPartRepository userPartRepository;


    @Override
    public SparePartTransactionDto createTransaction(CreateSparePartTransactionDto transactionDto) {
        SparePart sparePart = (SparePart) sparePartRepository.findByPartNumber(transactionDto.getPartNumber())
                .orElseThrow(() -> new IllegalArgumentException("Spare part not found with Part Number: " + transactionDto.getPartNumber()));
        UserPart userPart = userPartRepository.findBySparePart_SparePartId(sparePart.getSparePartId())
                .orElseThrow(() -> new IllegalArgumentException("No stock entry found for Spare Part ID: " + sparePart.getSparePartId()));
        if (transactionDto.getTransactionType() != TransactionType.CREDIT && transactionDto.getTransactionType() != TransactionType.DEBIT) {
            throw new IllegalArgumentException("Invalid transaction type! Allowed values: CREDIT or DEBIT.");
        }
        if (transactionDto.getTransactionType() == TransactionType.CREDIT && (transactionDto.getBillNo() == null || transactionDto.getBillNo().trim().isEmpty())) {
            throw new IllegalArgumentException("Bill number is required for CREDIT transactions.");
        }
        if (transactionDto.getTransactionType() == TransactionType.DEBIT) {
            if (transactionDto.getQuantity() <= 0) {
                throw new IllegalArgumentException("For DEBIT transactions, quantity must be greater than 0.");
            }
            if (userPart.getQuantity() < transactionDto.getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock! Available: " + userPart.getQuantity() + ", Requested: " + transactionDto.getQuantity());
            }
            userPart.setQuantity(userPart.getQuantity() - transactionDto.getQuantity());
        }
        if (transactionDto.getTransactionType() == TransactionType.CREDIT) {
            userPart.setQuantity(userPart.getQuantity() + transactionDto.getQuantity());
        }
        userPartRepository.save(userPart);
        SparePartTransaction transaction = SparePartTransaction.builder()
                .partNumber(sparePart.getPartNumber())
                .sparePartId(sparePart.getSparePartId())
                .partName(sparePart.getPartName())
                .manufacturer(sparePart.getManufacturer())
                .price(sparePart.getPrice())
                .qtyPrice(sparePart.getPrice() * transactionDto.getQuantity())  // Total price calculation
                .updateAt(sparePart.getUpdateAt())
                .transactionType(transactionDto.getTransactionType())
                .quantity(transactionDto.getQuantity())
                .transactionDate(java.time.LocalDateTime.now())
                .userId(transactionDto.getUserId())
                .billNo(transactionDto.getBillNo()) // Ensuring bill number is included
                .build();

        transaction = transactionRepository.save(transaction);
        return toDto(transaction);
    }





    @Override
    public SparePartTransactionDto getTransactionById(Integer transactionId) {
        SparePartTransaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + transactionId));
        return toDto(transaction);
    }

    @Override
    public List<SparePartTransactionDto> getAllTransactions() {
        List<SparePartTransaction> transactions = transactionRepository.findAll();
        return transactions.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public SparePartTransactionDto updateTransaction(Integer transactionId, SparePartTransactionDto transactionDto) {
        SparePartTransaction existingTransaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + transactionId));

        existingTransaction.setPartNumber(transactionDto.getPartNumber());
        existingTransaction.setSparePartId(transactionDto.getSparePartId());
        existingTransaction.setPartName(transactionDto.getPartName());
        existingTransaction.setManufacturer(transactionDto.getManufacturer());
        existingTransaction.setPrice(transactionDto.getPrice());
        existingTransaction.setQtyPrice(transactionDto.getQtyPrice());
        existingTransaction.setUpdateAt(transactionDto.getUpdateAt());
        existingTransaction.setTransactionType(transactionDto.getTransactionType());
        existingTransaction.setQuantity(transactionDto.getQuantity());
        existingTransaction.setTransactionDate(transactionDto.getTransactionDate());
        existingTransaction.setUserId(transactionDto.getUserId());
        existingTransaction.setBillNo(transactionDto.getBillNo());

        existingTransaction = transactionRepository.save(existingTransaction);
        return toDto(existingTransaction);
    }

    @Override
    public void deleteTransaction(Integer transactionId) {
        if (!transactionRepository.existsById(transactionId)) {
            throw new RuntimeException("Transaction not found with ID: " + transactionId);
        }
        transactionRepository.deleteById(transactionId);
    }

    @Override
    public List<SparePartTransactionDto> getByBillNo(String billNo) {
        List<SparePartTransaction> transactions = transactionRepository.findByBillNo(billNo);

        if (transactions.isEmpty()) {
            throw new RuntimeException("No transactions found with Bill No: " + billNo);
        }

        return transactions.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SparePartTransactionDto> getByUserId(Integer userId) {
        List<SparePartTransaction> transactions = transactionRepository.findByUserId(userId);

        if (transactions.isEmpty()) {
            throw new RuntimeException("No transactions found for User ID: " + userId);
        }

        return transactions.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SparePartTransactionDto> getTransactionsBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null) {
            throw new RuntimeException("Start date and end date cannot be null.");
        }
        if (endDate.isBefore(startDate)) {
            throw new RuntimeException("End date cannot be before start date.");
        }

        List<SparePartTransaction> transactions = transactionRepository.findByTransactionDateBetween(startDate, endDate);

        if (transactions.isEmpty()) {
            throw new RuntimeException("No transactions found between " + startDate + " and " + endDate);
        }

        return transactions.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


    // Entity to DTO conversion
    private SparePartTransactionDto toDto(SparePartTransaction transaction) {
        return SparePartTransactionDto.builder()
                .sparePartTransactionId(transaction.getSparePartTransactionId())
                .partNumber(transaction.getPartNumber())
                .sparePartId(transaction.getSparePartId())
                .partName(transaction.getPartName())
                .manufacturer(transaction.getManufacturer())
                .price(transaction.getPrice())
                .qtyPrice(transaction.getQtyPrice())
                .updateAt(transaction.getUpdateAt())
                .transactionType(transaction.getTransactionType())
                .quantity(transaction.getQuantity())
                .transactionDate(transaction.getTransactionDate())
                .userId(transaction.getUserId())
                .billNo(transaction.getBillNo())
                .build();
    }
}
