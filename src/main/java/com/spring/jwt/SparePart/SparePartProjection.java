package com.spring.jwt.SparePart;

import java.util.List;

public interface SparePartProjection {
    Integer getSparePartId();
    String getPartName();
    String getManufacturer();
    Long getPrice();
    String getPartNumber();
    List<byte[]> getPhoto();
}
