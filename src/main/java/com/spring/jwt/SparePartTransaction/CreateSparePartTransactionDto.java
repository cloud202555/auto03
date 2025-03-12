package com.spring.jwt.SparePartTransaction;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateSparePartTransactionDto {
    private String partNumber;
    private TransactionType transactionType;
    private String manufacturer;
    private Integer quantity;
    private Integer userId;
    private String billNo;
    private Integer vehicleRegId;
    private String customerName;
}
