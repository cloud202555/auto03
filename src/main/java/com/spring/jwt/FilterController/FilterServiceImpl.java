package com.spring.jwt.FilterController;

import com.spring.jwt.SparePart.*;
import com.spring.jwt.exception.PageNotFoundException;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilterServiceImpl implements FilterService {

    public final SparePartRepo filterRepository;

    public final SparePartMapper sparePartMapper;

    @Override
    public List<SparePartDto> searchBarFilter(String searchBarInput) {

        String[] tokens = searchBarInput.toLowerCase().trim().split("\\s+");

        List<SparePart> spareParts = filterRepository.findAll((root, query, cb) -> {
            root.fetch("photo", JoinType.LEFT);
            query.distinct(true);

            List<Predicate> tokenPredicates = new ArrayList<>();

            for (String token : tokens) {
                String pattern = "%" + token + "%";

                Predicate orForThisToken = cb.or(
                        cb.like(cb.lower(root.get("partName")), pattern),
                        cb.like(cb.lower(root.get("description")), pattern),
                        cb.like(cb.lower(root.get("manufacturer")), pattern),
                        cb.like(cb.function("concat", String.class, cb.literal(""), root.get("partNumber")) , pattern)
                );

                tokenPredicates.add(orForThisToken);
            }

            return cb.and(tokenPredicates.toArray(new Predicate[0]));
        });

        if (spareParts.isEmpty()) {
            throw new PageNotFoundException("No spare parts found for the given search keyword.");
        }
        return spareParts.stream()
                .map(sparePartMapper::toDto)
                .collect(Collectors.toList());
    }

}
