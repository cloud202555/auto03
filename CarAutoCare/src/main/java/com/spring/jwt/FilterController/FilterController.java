package com.spring.jwt.FilterController;

import com.spring.jwt.SparePart.SparePartDto;
import com.spring.jwt.SparePart.SparePartService;
import com.spring.jwt.exception.PageNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Filter")
public class add FilterController {

    private final  FilterService filterService;

    public FilterController(FilterService filterService) {
        this.filterService = filterService;
    }

    @GetMapping("/searchBarFilter")
    public ResponseEntity<SparePartAllDto> searchBarFilter(@RequestParam String searchBarInput) {
        try {
            List<SparePartDto> sparePartDtos = filterService.searchBarFilter(searchBarInput);

            SparePartAllDto responseDto = new SparePartAllDto("success");
            responseDto.setList(sparePartDtos);

            return ResponseEntity.ok(responseDto);
        } catch (PageNotFoundException ex) {
            SparePartAllDto responseDto = new SparePartAllDto("unsuccess");
            responseDto.setException(ex.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
        }
    }


}
