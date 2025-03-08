package com.spring.jwt.SparePartTransaction;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SparePartTransactionDto {

    private Integer sparePartTransactionId;
    private Long partNumber;
    private Integer sparePartId;
    private String partName;
    private String manufacturer;
    private Long price;
    private Long qtyPrice;
    private LocalDate updateAt;
    private TransactionType transactionType;
    private Integer quantity;
    private LocalDateTime transactionDate;
    private Integer userId;
    private String billNo;
}
