package com.spring.jwt.UserParts;

import com.spring.jwt.SparePart.PaginatedResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserPartServiceImpl implements UserPartService {

    private final UserPartRepository userPartRepository;

    public UserPartServiceImpl(UserPartRepository userPartRepository) {
        this.userPartRepository = userPartRepository;
    }

    @Override
    public UserPartDto getUserPartById(Integer userPartId) {
        return userPartRepository.findById(userPartId)
                .map(UserPartDto::new)
                .orElseThrow(() -> new RuntimeException("UserPart not found with id " + userPartId));
    }

    @Override
    public PaginatedResponse<UserPartDto> getAllUserParts(int page, int size) {
        Sort sort = Sort.by("userPartId").descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<UserPartProjection> userPartPage = userPartRepository.findAllProjected(pageable);

        if (userPartPage.isEmpty()) {
            throw new RuntimeException("No data found");
        }

        List<UserPartDto> userPartDtoList = userPartPage
                .map(projection -> UserPartDto.builder()
                        .userPartId(projection.getUserPartId())
                        .partNumber(projection.getPartNumber())
                        .partName(projection.getPartName())
                        .manufacturer(projection.getManufacturer())
                        .quantity(projection.getQuantity())
                        .price(projection.getPrice())
                        .updateAt(projection.getUpdateAt())
                        .description(projection.getDescription())
                        .build())
                .getContent();

        return new PaginatedResponse<>(
                userPartDtoList,
                userPartPage.getTotalPages(),
                userPartPage.getTotalElements(),
                page
        );
    }
    @Override
    public Integer getQuantityByPartNumber(String partNumber) {
        return userPartRepository.findQuantityByPartNumber(partNumber)
                .orElseThrow(() -> new IllegalArgumentException("No stock entry found for Part Number: " + partNumber));
    }



}
