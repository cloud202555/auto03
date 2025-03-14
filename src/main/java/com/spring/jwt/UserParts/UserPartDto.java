package com.spring.jwt.UserParts;

import jakarta.persistence.Column;
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
    private String partNumber;
    private Integer cGST;
    private Integer sGST;
    private Integer totalGST;
    private Integer buyingPrice;

    public UserPartDto(UserPart userPart) {
        this.userPartId = userPart.getUserPartId();
        this.lastUpdate = userPart.getLastUpdate();
        this.partNumber = userPart.getPartNumber();
        this.updateAt = userPart.getUpdateAt();
        this.price = userPart.getPrice();
        this.manufacturer = userPart.getManufacturer();
        this.description = userPart.getDescription();
        this.partName = userPart.getPartName();
        this.quantity = userPart.getQuantity();
        this.cGST=userPart.getCGST();
        this.sGST=userPart.getSGST();
        this.totalGST=userPart.getTotalGST();
        this.buyingPrice=userPart.getBuyingPrice();

    }

}
