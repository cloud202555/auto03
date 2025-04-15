package com.spring.jwt.FilterController;

import com.spring.jwt.SparePart.*;
import com.spring.jwt.exception.PageNotFoundException;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    @Override
    public List<SpareFilterDto> searchSpareParts(String keyword, int limit) {
        String[] tokens = keyword.trim().toLowerCase().split("\\s+");

        Specification<SparePart> spec = (root, query, cb) -> {
            List<Predicate> tokenPredicates = new ArrayList<>();
            for (String token : tokens) {
                String pattern = "%" + token + "%";
                Predicate tokenPredicate = cb.or(
                        cb.like(cb.lower(root.get("partName")), pattern),
                        cb.like(cb.lower(root.get("description")), pattern),
                        cb.like(cb.lower(root.get("manufacturer")), pattern),
                        cb.like(cb.lower(cb.function("concat", String.class, cb.literal(""), root.get("partNumber"))), pattern)
                );
                tokenPredicates.add(tokenPredicate);
            }
            return cb.and(tokenPredicates.toArray(new Predicate[0]));
        };

        Pageable pageable = PageRequest.of(0, limit);
        Page<SparePart> pageResult = filterRepository.findAll(spec, pageable);

        return pageResult.getContent().stream()
                .map(sparePart -> SpareFilterDto.builder()
                        .sparePartId(sparePart.getSparePartId())
                        .partName(sparePart.getPartName())
                        .description(sparePart.getDescription())
                        .manufacturer(sparePart.getManufacturer())
                        .price(sparePart.getPrice())
                        .partNumber(sparePart.getPartNumber())
                        .cGST(sparePart.getCGST())
                        .sGST(sparePart.getSGST())
                        .totalGST(sparePart.getTotalGST())
                        .buyingPrice(sparePart.getBuyingPrice())
                        .build())
                .collect(Collectors.toList());
    }

}
