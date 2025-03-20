package com.spring.jwt.UserParts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;


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
    public Page<UserPartDto> getAllUserParts(int page, int size) {
        Sort sort = Sort.by("userPartId").descending(); // Sorting by userPartId in descending order
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<UserPart> userPartPage = userPartRepository.findAll(pageable);
        if (userPartPage.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return userPartPage.map(UserPartDto::new);
    }

}
