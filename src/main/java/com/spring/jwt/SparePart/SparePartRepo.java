package com.spring.jwt.SparePart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SparePartRepo extends JpaRepository<SparePart, Integer> {
    Optional<Object> findByPartNumber(Long partNumber);
}
