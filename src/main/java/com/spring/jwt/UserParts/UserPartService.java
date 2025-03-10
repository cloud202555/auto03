package com.spring.jwt.UserParts;

import java.util.List;
import java.util.Optional;

public interface UserPartService {

    UserPartDto getUserPartById(Integer userPartId);

    List<UserPartDto> getAllUserParts();

}
