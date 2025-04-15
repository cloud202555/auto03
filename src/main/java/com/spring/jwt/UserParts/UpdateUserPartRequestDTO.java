package com.spring.jwt.UserParts;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateUserPartRequestDTO {
    private Integer userPartId;

    private String partName;

    private String description;

    private String manufacturer;

    private Long price;

    private LocalDate updateAt;

    private String partNumber;

    private Integer quantity;

    private Integer cGST;

    private Integer sGST;

    private Integer totalGST;

    private Integer buyingPrice;
}
