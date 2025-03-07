package com.spring.jwt.SparePartTransaction;

import com.spring.jwt.Appointment.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/sparePartTransactions")
public class SparePartTransactionController {

    private final SparePartTransactionService transactionService;

    public SparePartTransactionController(SparePartTransactionService transactionService) {
        this.transactionService = transactionService;
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
}
