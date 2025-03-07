package com.spring.jwt.UserParts;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPartDto {
    private Integer userPartId;
    private Integer quantity;
    private String lastUpdate;
    private Integer sparePartId;
    private String partName;
    private String description;
    private String manufacturer;
    private Long price;
    private LocalDate updateAt;
    private List<byte[]> photo;
    private Long partNumber;
}
