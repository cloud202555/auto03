package com.spring.jwt.UserParts;

import com.spring.jwt.SparePart.SparePart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserPartRepository extends JpaRepository<UserPart, Integer> {
    Optional<UserPart> findBySparePart_SparePartId(Integer sparePartId);

    void deleteBySparePart(SparePart sparePart);

    @Query("select u.userPartId as userPartId, u.partNumber as partNumber, u.partName as partName, " +
            "u.manufacturer as manufacturer, u.quantity as quantity, u.price as price,u.description as description, u.updateAt as updateAt " +
            "from UserPart u")
    Page<UserPartProjection> findAllProjected(Pageable pageable);

    Page<UserPart> findAll(Pageable pageable);
}
