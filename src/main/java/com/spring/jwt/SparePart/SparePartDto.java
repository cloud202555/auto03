package com.spring.jwt.SparePart;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class SparePartDto {

    private Integer sparePartId;

    @NotBlank(message = "Part name cannot be empty.")
    private String partName;

    @NotBlank(message = "Description cannot be empty.")
    private String description;

    @NotBlank(message = "Manufacturer cannot be empty.")
    private String manufacturer;

    @NotNull(message = "Price is required.")
    private Long price;

    @NotNull(message = "Update date is required.")
    private LocalDate updateAt;

    private List<String> photo;

    @NotNull(message = "Part number is required.")
    private Long partNumber;
}
