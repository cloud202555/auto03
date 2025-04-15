package com.spring.jwt.SparePartTransaction;

import com.spring.jwt.Appointment.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sparePartTransactions")
public class SparePartTransactionController {

    private final SparePartTransactionService transactionService;
    private final SparePartTransactionRepository transactionRepository;

    public SparePartTransactionController(SparePartTransactionService transactionService,
                                          SparePartTransactionRepository transactionRepository) {
        this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseDto<SparePartTransactionDto>> createTransaction(
            @RequestBody CreateSparePartTransactionDto transactionDto) {
        try {
            SparePartTransactionDto createdTransaction = transactionService.createTransaction(transactionDto);
            return ResponseEntity.ok(ResponseDto.success("Transaction created successfully", createdTransaction));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDto.error("Failed to create transaction", e.getMessage()));
        }
    }

    @GetMapping("/GetById")
    public ResponseEntity<ResponseDto<SparePartTransactionDto>> getTransactionById(@RequestParam Integer transactionId) {
        try {
            SparePartTransactionDto transaction = transactionService.getTransactionById(transactionId);
            return ResponseEntity.ok(ResponseDto.success("Transaction retrieved successfully", transaction));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDto.error("Transaction not found", e.getMessage()));
        }
    }

    @GetMapping("getAll")
    public ResponseEntity<ResponseDto<List<SparePartTransactionDto>>> getAllTransactions() {
        try {
            List<SparePartTransactionDto> transactions = transactionService.getAllTransactions();
            return ResponseEntity.ok(ResponseDto.success("Transactions retrieved successfully", transactions));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDto.error("Failed to retrieve transactions", e.getMessage()));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto<SparePartTransactionDto>> updateTransaction(
            @RequestParam Integer transactionId,
            @RequestBody SparePartTransactionDto transactionDto) {
        try {
            SparePartTransactionDto updatedTransaction = transactionService.updateTransaction(transactionId, transactionDto);
            return ResponseEntity.ok(ResponseDto.success("Transaction updated successfully", updatedTransaction));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDto.error("Failed to update transaction", e.getMessage()));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto<Void>> deleteTransaction(@RequestParam Integer transactionId) {
        try {
            transactionService.deleteTransaction(transactionId);
            return ResponseEntity.ok(ResponseDto.success("Transaction deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDto.error("Failed to delete transaction", e.getMessage()));
        }
    }

    @GetMapping("/getByBillNo")
    public ResponseEntity<ResponseDto<List<SparePartTransactionDto>>> getByBillNo(@RequestParam String billNo) {
        try {
            List<SparePartTransactionDto> transactions = transactionService.getByBillNo(billNo);
            return ResponseEntity.ok(ResponseDto.success("Transactions retrieved successfully", transactions));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDto.error("No transactions found with Bill No", e.getMessage()));
        }
    }

    @GetMapping("/userId")
    public ResponseEntity<ResponseDto<List<SparePartTransactionDto>>> getByUserId(@RequestParam Integer userId) {
        try {
            List<SparePartTransactionDto> transactions = transactionService.getByUserId(userId);
            return ResponseEntity.ok(ResponseDto.success("Transactions retrieved successfully", transactions));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDto.error("No transactions found for User ID", e.getMessage()));
        }
    }

    @GetMapping("/between-dates")
    public ResponseEntity<ResponseDto<List<SparePartTransactionDto>>> getTransactionsBetweenDates(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        try {
            List<SparePartTransactionDto> transactions = transactionService.getTransactionsBetweenDates(startDate, endDate);
            return ResponseEntity.ok(ResponseDto.success("Transactions retrieved successfully", transactions));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDto.error("Failed to retrieve transactions", e.getMessage()));
        }
    }
    @GetMapping("/vehicleRegId")
    public ResponseEntity<ResponseDto<List<SparePartTransactionDto>>> getByVehicleRegId(@RequestParam Integer vehicleRegId) {
        try {
            List<SparePartTransactionDto> transactions = transactionService.getByVehicleRegId(vehicleRegId);
            return ResponseEntity.ok(ResponseDto.success("Transactions retrieved successfully", transactions));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDto.error("No transactions found for Vehicle Registration ID", e.getMessage()));
        }
    }

    @GetMapping("/byPartNumberAndDates")
    public ResponseEntity<ResponseDto<List<SparePartTransactionDto>>> getByPartNumberAndTransactionsBetweenDates(
            @RequestParam Integer sparePartId,
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        try {
            List<SparePartTransactionDto> transactions = transactionService.getByPartNumberAndTransactionsBetweenDates(sparePartId, startDate, endDate);
            return ResponseEntity.ok(ResponseDto.success("Transactions retrieved successfully", transactions));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDto.error("No transactions found for the given criteria", e.getMessage()));
        }
    }

    @GetMapping("/byTransactionTypeAndNameAndDateRange")
    public ResponseEntity<ResponseDto<List<SparePartTransactionDto>>> getByTransactionTypeAndNameAndDateRange(
            @RequestParam TransactionType transactionType,
            @RequestParam String name,
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        try {
            List<SparePartTransactionDto> transactions = transactionService.getByTransactionTypeAndNameAndDateRange(transactionType, name, startDate, endDate);
            return ResponseEntity.ok(ResponseDto.success("Transactions retrieved successfully", transactions));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDto.error("No transactions found for given filters", e.getMessage()));
        }
    }

    @GetMapping("/byNameOrPartNumber")
    public ResponseEntity<ResponseDto<List<SparePartTransactionDto>>> getByNameOrPartNumber(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String partNumber) {
        try {
            List<SparePartTransactionDto> transactions = transactionService.getByNameOrPartNumber(name, partNumber);
            return ResponseEntity.ok(ResponseDto.success("Transactions retrieved successfully", transactions));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDto.error("No transactions found for given filters", e.getMessage()));
        }

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
                .cGST(transaction.getCGST())
                .sGST(transaction.getSGST())
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

    @GetMapping("/byTransactionTypeAndDateRange")
    public ResponseEntity<ResponseDto<List<SparePartTransactionDto>>> getByTransactionTypeAndDateRange(
            @RequestParam TransactionType transactionType,
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        try {
            List<SparePartTransactionDto> transactions = transactionService.getCreditTransactionsByDateRange(
                    transactionType, startDate, endDate);
            return ResponseEntity.ok(ResponseDto.success("Transactions retrieved successfully", transactions));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDto.error("No transactions found for given filters", e.getMessage()));
        }
    }
}
