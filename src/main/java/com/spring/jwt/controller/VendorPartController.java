package com.spring.jwt.controller;

import com.spring.jwt.dto.VendorPartDto;
import com.spring.jwt.exception.ResourceNotFoundException;
import com.spring.jwt.service.VendorPartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.spring.jwt.Appointment.ResponseDto;

import java.util.List;

@RestController
@RequestMapping("/vendorParts")
public class VendorPartController {

    @Autowired
    private VendorPartService vendorPartService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto<VendorPartDto>> create(@RequestBody VendorPartDto dto) {
        try {
            VendorPartDto created = vendorPartService.create(dto);
            return ResponseEntity.ok(
                    new ResponseDto<>("Vendor part created successfully", created, null)
            );
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new ResponseDto<>("Failed to create Vendor part", null, e.getMessage())
            );
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto<VendorPartDto>> update(
            @RequestParam Integer id,
            @RequestBody VendorPartDto dto) {
        try {
            VendorPartDto updated = vendorPartService.update(id, dto);
            return ResponseEntity.ok(
                    new ResponseDto<>("Vendor part updated successfully", updated, null)
            );
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(
                    new ResponseDto<>("Vendor part not found", null, e.getMessage())
            );
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new ResponseDto<>("Failed to update Vendor part", null, e.getMessage())
            );
        }
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<ResponseDto<VendorPartDto>> getById(@RequestParam Integer id) {
        try {
            VendorPartDto dto = vendorPartService.getById(id);
            return ResponseEntity.ok(
                    new ResponseDto<>("Vendor part fetched successfully", dto, null)
            );
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(
                    new ResponseDto<>("Vendor part not found", null, e.getMessage())
            );
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new ResponseDto<>("Failed to fetch Vendor part", null, e.getMessage())
            );
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<ResponseDto<List<VendorPartDto>>> getAll() {
        try {
            List<VendorPartDto> list = vendorPartService.getAll();
            return ResponseEntity.ok(
                    new ResponseDto<>("All vendor parts fetched", list, null)
            );
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new ResponseDto<>("Failed to fetch vendor parts", null, e.getMessage())
            );
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto<Void>> delete(@RequestParam Integer id) {
        try {
            vendorPartService.delete(id);
            return ResponseEntity.ok(
                    new ResponseDto<>("Vendor part deleted successfully", null, null)
            );
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(
                    new ResponseDto<>("Vendor part not found", null, e.getMessage())
            );
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new ResponseDto<>("Failed to delete Vendor part", null, e.getMessage())
            );
        }
    }

    @GetMapping("/get-by-part-number")
    public ResponseEntity<ResponseDto<List<VendorPartDto>>> getByPartNumber(@RequestParam String partNumber) {
        try {
            List<VendorPartDto> parts = vendorPartService.getByPartNumber(partNumber);
            return ResponseEntity.ok(
                    new ResponseDto<>("Vendor parts with part number fetched", parts, null)
            );
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(
                    new ResponseDto<>("No Vendor parts found with part number", null, e.getMessage())
            );
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new ResponseDto<>("Failed to fetch Vendor parts", null, e.getMessage())
            );
        }
    }
}
