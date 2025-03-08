package com.spring.jwt.SparePart;

import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public class SparePartSpecifications {

    public static Specification<SparePart> searchByKeyword(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (keyword == null || keyword.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            String pattern = "%" + keyword.toLowerCase() + "%";

            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("partName")), pattern));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), pattern));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("manufacturer")), pattern));
            predicates.add(criteriaBuilder.like(root.get("partNumber").as(String.class), pattern));

            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }
}

