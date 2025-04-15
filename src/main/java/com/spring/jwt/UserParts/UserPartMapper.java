package com.spring.jwt.UserParts;

import org.springframework.stereotype.Component;

@Component
public class UserPartMapper {

    public UserPartDto toDto(UserPart userPart) {
        if (userPart == null) {
            return null;
        }

        UserPartDto dto = new UserPartDto();
        dto.setUserPartId(userPart.getUserPartId());
        dto.setPartName(userPart.getPartName());
        dto.setDescription(userPart.getDescription());
        dto.setManufacturer(userPart.getManufacturer());
        dto.setPrice(userPart.getPrice());
        dto.setUpdateAt(userPart.getUpdateAt());
        dto.setPartNumber(userPart.getPartNumber());
        dto.setQuantity(userPart.getQuantity());
        dto.setLastUpdate(userPart.getLastUpdate());
        dto.setCGST(userPart.getCGST());
        dto.setSGST(userPart.getSGST());
        dto.setTotalGST(userPart.getTotalGST());
        dto.setBuyingPrice(userPart.getBuyingPrice());

        if(userPart.getSparePart() != null) {
            dto.setSparePartId(userPart.getSparePart().getSparePartId());
        }

        return dto;
    }

}
