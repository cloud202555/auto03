package com.spring.jwt.UserParts;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserPartService {

    UserPartDto getUserPartById(Integer userPartId);

    public Page<UserPartDto> getAllUserParts(int page, int size);

}
