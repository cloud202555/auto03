package com.spring.jwt.UserParts;

import com.spring.jwt.SparePart.PaginatedResponse;
import com.spring.jwt.SparePart.SparePartDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserPartService {

    UserPartDto getUserPartById(Integer userPartId);

    public PaginatedResponse<UserPartDto> getAllUserParts(int page, int size);

    Integer getQuantityByPartNumber(String partNumber);
}
