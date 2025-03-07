package com.spring.jwt.SparePartTransaction;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateSparePartTransactionDto {
    private Long partNumber;
    private TransactionType transactionType;
    private Integer quantity;
    private Integer userId;
    private String billNo;
}
