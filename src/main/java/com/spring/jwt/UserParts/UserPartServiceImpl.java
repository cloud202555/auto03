package com.spring.jwt.UserParts;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<UserPartDto> getAllUserParts() {

        List<UserPart> userPart = userPartRepository.findAll();
                if(userPart.isEmpty()){
                    throw new RuntimeException("Internal Server Error Cannot Find Data");
                }
                 return userPart.stream()
                         .map(UserPartDto::new)
                         .collect(Collectors.toList());
                }
}
