package com.spring.jwt.FilterController;

import com.spring.jwt.SparePart.SparePartDto;
import lombok.Data;

import java.util.List;


@Data
public class SparePartAllDto {
    private String message;
    private List<SparePartDto> list;
    private String exception;

    public SparePartAllDto(String message) {
        this.message = message;
    }
}

