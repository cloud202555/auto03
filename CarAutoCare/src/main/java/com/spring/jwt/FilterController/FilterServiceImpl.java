package com.spring.jwt.FilterController;

import com.spring.jwt.SparePart.*;
import com.spring.jwt.exception.PageNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilterServiceImpl implements FilterService {

    public final SparePartRepo filterRepository;

    public final SparePartMapper sparePartMapper;

    @Override
    public List<SparePartDto> searchBarFilter(String searchBarInput) {

        List<SparePart> spareParts = filterRepository.findAll(SparePartSpecifications.searchByKeyword(searchBarInput));

        if (spareParts.isEmpty()) {
            throw new PageNotFoundException("No spare parts found for the given search keyword.");
        }

        return spareParts.stream()
                .map(sparePartMapper::toDto)
                .collect(Collectors.toList());
    }

}
