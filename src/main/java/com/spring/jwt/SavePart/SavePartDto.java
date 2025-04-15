package com.spring.jwt.SavePart;

import lombok.Data;

@Data
public class SavePartDto {

    private Integer savePartId;

    private  Integer sparePartId;

    private Integer userId;





    public SavePartDto() {
    }
    public SavePartDto(SavePart savePart) {
        this.savePartId = savePart.getSavePartId();
        this.sparePartId = savePart.getSparePartId();
        this.userId = savePart.getUserId();
    }
}
