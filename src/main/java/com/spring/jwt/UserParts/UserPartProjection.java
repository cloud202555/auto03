package com.spring.jwt.UserParts;

import java.time.LocalDate;

public interface UserPartProjection {
    Integer getUserPartId();
    Integer getQuantity();
    String getLastUpdate();
    Integer getSparePartId();
    String getPartName();
    String getDescription();
    String getManufacturer();
    Long getPrice();
    LocalDate getUpdateAt();
    String getPartNumber();
    Integer getCGST();
    Integer getSGST();
    Integer getTotalGST();
    Integer getBuyingPrice();

}
