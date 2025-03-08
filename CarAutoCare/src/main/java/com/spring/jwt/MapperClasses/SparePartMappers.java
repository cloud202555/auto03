package com.spring.jwt.MapperClasses;

import com.spring.jwt.SparePart.SparePart;
import com.spring.jwt.SparePart.SparePartDto;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class SparePartMappers {

    public static SparePartDto toDto(SparePart sparePart) {
        return SparePartDto.builder()
                .sparePartId(sparePart.getSparePartId())
                .partName(sparePart.getPartName())
                .description(sparePart.getDescription())
                .manufacturer(sparePart.getManufacturer())
                .price(sparePart.getPrice())
                .partNumber(sparePart.getPartNumber())
                .updateAt(sparePart.getUpdateAt())
                .photo(convertPhotosToBase64(sparePart.getPhoto()))
                .build();
    }

    private static List<String> convertPhotosToBase64(List<byte[]> photos) {
        return photos.stream()
                .map(photo -> Base64.getEncoder().encodeToString(photo))
                .collect(Collectors.toList());
    }
}
