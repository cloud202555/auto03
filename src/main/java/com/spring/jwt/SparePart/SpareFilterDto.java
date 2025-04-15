package com.spring.jwt.SparePart;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpareFilterDto {

    private Integer sparePartId;
    private String partName;
    private String description;
    private String manufacturer;
    private Long price;
    private String partNumber;
    private Integer cGST;
    private Integer sGST;
    private Integer totalGST;
    private Integer buyingPrice;

}
