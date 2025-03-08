package com.spring.jwt.FilterController;

import com.spring.jwt.SparePart.SparePartDto;

import java.util.List;

public interface FilterService {
    List<SparePartDto> searchBarFilter(String searchBarInput);
}
