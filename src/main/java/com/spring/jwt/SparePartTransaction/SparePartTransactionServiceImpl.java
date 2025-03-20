package com.spring.jwt.SparePartTransaction;

import com.spring.jwt.SparePart.SparePart;
import com.spring.jwt.SparePart.SparePartRepo;
import com.spring.jwt.UserParts.UserPart;
import com.spring.jwt.UserParts.UserPartRepository;
import com.spring.jwt.VehicleReg.VehicleRegRepository;
import com.spring.jwt.entity.VehicleReg;
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
    private SparePartRepo sparePartRepository;

    @Autowired
    private UserPartRepository userPartRepository;
    @Autowired
    private VehicleRegRepository vehicleRegRepository;

    @Override
    public SparePartTransactionDto createTransaction(CreateSparePartTransactionDto transactionDto) {

        if (transactionDto.getTransactionType() != TransactionType.CREDIT && transactionDto.getTransactionType() != TransactionType.DEBIT) {
            throw new IllegalArgumentException("Invalid transaction type! Allowed values: CREDIT or DEBIT.");
        }

        Integer userId = transactionDto.getUserId();

        if (transactionDto.getTransactionType() == TransactionType.DEBIT) {
            if (userId == null && transactionDto.getVehicleRegId() != null) {
                VehicleReg vehicleReg = vehicleRegRepository
                        .findUserIdByVehicleRegId(transactionDto.getVehicleRegId())
                        .orElseThrow(() -> new IllegalArgumentException("No user found for Vehicle Registration ID: " + transactionDto.getVehicleRegId()));
                userId = vehicleReg.getUserId();
            }
            if (userId == null) {
                throw new IllegalArgumentException("Either userId or vehicleRegId must be provided for DEBIT transactions.");
            }
        }

        SparePart sparePart = sparePartRepository.findByPartNumberAndManufacturer(transactionDto.getPartNumber(), transactionDto.getManufacturer())
                .orElseThrow(() -> new IllegalArgumentException("Spare part not found with Part Number: " + transactionDto.getPartNumber()));

        UserPart userPart = userPartRepository.findBySparePart_SparePartId(sparePart.getSparePartId())
                .orElseThrow(() -> new IllegalArgumentException("No stock entry found for Spare Part ID: " + sparePart.getSparePartId()));

        if (transactionDto.getTransactionType() == TransactionType.CREDIT &&
                (transactionDto.getBillNo() == null || transactionDto.getBillNo().trim().isEmpty())) {
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

        // **Calculate GST and Final Price**
        double cgstAmount = (sparePart.getPrice() * sparePart.getCGST()) / 100.0;
        double sgstAmount = (sparePart.getPrice() * sparePart.getSGST()) / 100.0;
        double totalGST = cgstAmount + sgstAmount;
        double finalPrice = sparePart.getPrice() + totalGST;

        // **Save Transaction**
        SparePartTransaction transaction = SparePartTransaction.builder()
                .partNumber(sparePart.getPartNumber())
                .sparePartId(sparePart.getSparePartId())
                .partName(sparePart.getPartName())
                .manufacturer(sparePart.getManufacturer())
                .customerName(transactionDto.getCustomerName())
                .price((long) finalPrice) // Price including GST
                .qtyPrice((long) (finalPrice * transactionDto.getQuantity())) // GST applied to total qty
                .updateAt(sparePart.getUpdateAt())
                .transactionType(transactionDto.getTransactionType())
                .quantity(transactionDto.getQuantity())
                .vehicleRegId(transactionDto.getTransactionType() == TransactionType.DEBIT ? transactionDto.getVehicleRegId() : null)
                .transactionDate(LocalDateTime.now())
                .userId(userId)
                .billNo(transactionDto.getBillNo())
                .name(transactionDto.getName())
                .totalGST((int) totalGST) // Save Total GST
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
        existingTransaction.setCustomerName(transactionDto.getCustomerName());

        existingTransaction = transactionRepository.save(existingTransaction);
        return toDto(existingTransaction);
    }

    @Override
    public void deleteTransaction(Integer transactionId) {
        // Fetch the transaction
        SparePartTransaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + transactionId));

        // Fetch the associated UserPart
        UserPart userPart = userPartRepository.findBySparePart_SparePartId(transaction.getSparePartId())
                .orElseThrow(() -> new RuntimeException("No stock entry found for Spare Part ID: " + transaction.getSparePartId()));

        // Adjust UserPart quantity based on transaction type
        if (transaction.getTransactionType() == TransactionType.CREDIT) {
            if (userPart.getQuantity() < transaction.getQuantity()) {
                throw new RuntimeException("Cannot delete CREDIT transaction: Not enough stock to reverse.");
            }
            userPart.setQuantity(userPart.getQuantity() - transaction.getQuantity()); // Reduce stock
        } else if (transaction.getTransactionType() == TransactionType.DEBIT) {
            userPart.setQuantity(userPart.getQuantity() + transaction.getQuantity()); // Add stock back
        }
        userPartRepository.save(userPart);

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
    public List<SparePartTransactionDto> getByVehicleRegId(Integer vehicleRegId) throws RuntimeException {
        if (vehicleRegId == null) {
            throw new IllegalArgumentException("Vehicle Registration ID cannot be null.");
        }

        VehicleReg vehicleReg = vehicleRegRepository
                .findById(vehicleRegId)
                .orElseThrow(() -> new IllegalArgumentException("No user found for Vehicle Registration ID: " + vehicleRegId));
        Integer userId = vehicleReg.getUserId();
        List<SparePartTransaction> transactions = transactionRepository.findByVehicleRegId(vehicleRegId);

        if (transactions.isEmpty()) {
            throw new RuntimeException("No transactions found for Vehicle Registration ID: " + vehicleRegId);
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

    @Override
    public List<SparePartTransactionDto> getByPartNumberAndTransactionsBetweenDates(Integer sparePartId, LocalDateTime startDate, LocalDateTime endDate) {

        if (sparePartId == null) {
            throw new IllegalArgumentException("Spare Part ID cannot be null.");
        }
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date cannot be null.");
        }
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date.");
        }

        SparePart sparePart = sparePartRepository.findById(sparePartId)
                .orElseThrow(() -> new RuntimeException("Spare part not found with ID: " + sparePartId));

        List<SparePartTransaction> transactions = transactionRepository.findBySparePartIdAndTransactionDateBetween(
                sparePartId, startDate, endDate);

        if (transactions.isEmpty()) {
            throw new RuntimeException("No transactions found for Spare Part ID: " + sparePartId +
                    " between " + startDate + " and " + endDate);
        }

        return transactions.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SparePartTransactionDto> getByTransactionTypeAndNameAndDateRange(TransactionType transactionType, String name, LocalDateTime startDate, LocalDateTime endDate) {
        if (transactionType == null || name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Transaction type and name cannot be null or empty.");
        }
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date cannot be null.");
        }
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date.");
        }

        List<SparePartTransaction> transactions = transactionRepository.findByTransactionTypeAndNameAndTransactionDateBetween(
                transactionType, name, startDate, endDate);

        if (transactions.isEmpty()) {
            throw new RuntimeException("No transactions found for type: " + transactionType +
                    ", name: " + name + ", between " + startDate + " and " + endDate);
        }

        return transactions.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SparePartTransactionDto> getByNameOrPartNumber(String name, String partNumber) {
        if ((name == null || name.trim().isEmpty()) && (partNumber == null || partNumber.trim().isEmpty())) {
            throw new IllegalArgumentException("Either name or part number must be provided.");
        }

        List<SparePartTransaction> transactions;

        if (name != null && !name.trim().isEmpty() && partNumber != null && !partNumber.trim().isEmpty()) {
            transactions = transactionRepository.findByNameOrPartNumber(name, partNumber);
        } else if (name != null && !name.trim().isEmpty()) {
            transactions = transactionRepository.findByName(name);
        } else {
            transactions = transactionRepository.findByPartNumber(partNumber);
        }

        if (transactions.isEmpty()) {
            throw new RuntimeException("No transactions found for given filters.");
        }

        return transactions.stream().map(this::toDto).collect(Collectors.toList());
    }


    private SparePartTransactionDto toDto(SparePartTransaction transaction) {
        return SparePartTransactionDto.builder()
                .sparePartTransactionId(transaction.getSparePartTransactionId())
                .partNumber(transaction.getPartNumber())
                .sparePartId(transaction.getSparePartId())
                .partName(transaction.getPartName())
                .manufacturer(transaction.getManufacturer())
                .vehicleRegId(transaction.getVehicleRegId())
                .price(transaction.getPrice())
                .qtyPrice(transaction.getQtyPrice())
                .updateAt(transaction.getUpdateAt())
                .transactionType(transaction.getTransactionType())
                .quantity(transaction.getQuantity())
                .transactionDate(transaction.getTransactionDate())
                .userId(transaction.getUserId())
                .billNo(transaction.getBillNo())
                .customerName(transaction.getCustomerName())
                .name(transaction.getName())
                .build();
    }

}
