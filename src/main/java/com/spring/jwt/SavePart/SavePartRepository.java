package com.spring.jwt.SavePart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavePartRepository extends JpaRepository<SavePart, Integer> {

    List<SavePart> findByUserId(Integer userId);

    Optional<SavePart> findByUserIdAndSparePartId(int userId, Integer sparePartId);
}
