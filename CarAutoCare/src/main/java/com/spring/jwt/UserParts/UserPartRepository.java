package com.spring.jwt.UserParts;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserPartRepository extends JpaRepository<UserPart, Integer> {
    Optional<UserPart> findBySparePart_SparePartId(Integer sparePartId);
}
