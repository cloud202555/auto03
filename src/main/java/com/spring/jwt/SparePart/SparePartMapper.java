package com.spring.jwt.SparePart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SparePartMapper {

    public SparePartDto toDto(SparePart entity) {
        if (entity == null) {
            return null;
        }
        return SparePartDto.builder()
                .sparePartId(entity.getSparePartId())
                .partName(entity.getPartName())
                .description(entity.getDescription())
                .manufacturer(entity.getManufacturer())
                .price(entity.getPrice())
                .updateAt(entity.getUpdateAt())
                .partNumber(entity.getPartNumber())
                .build();
    }

    public SparePart toEntity(SparePartDto dto) {
        if (dto == null) {
            return null;
        }
        return SparePart.builder()
                .sparePartId(dto.getSparePartId())
                .partName(dto.getPartName())
                .description(dto.getDescription())
                .manufacturer(dto.getManufacturer())
                .price(dto.getPrice())
                .updateAt(dto.getUpdateAt())
                .partNumber(dto.getPartNumber())
                .build();
    }

    private List<String> convertPhotosToBase64(List<byte[]> photos) {
        if (photos == null) {
            return List.of();
        }
        return photos.stream()
                .map(photo -> Base64.getEncoder().encodeToString(photo))
                .collect(Collectors.toList());
    }

    private List<byte[]> convertBase64ToPhotos(List<String> photos) {
        if (photos == null) {
            return List.of();
        }
        return photos.stream()
                .map(photo -> Base64.getDecoder().decode(photo))
                .collect(Collectors.toList());
    }
}
