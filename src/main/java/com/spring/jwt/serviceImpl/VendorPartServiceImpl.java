package com.spring.jwt.serviceImpl;


import com.spring.jwt.dto.VendorPartDto;
import com.spring.jwt.entity.VendorPart;
import com.spring.jwt.exception.ResourceNotFoundException;
import com.spring.jwt.repository.VendorPartRepository;
import com.spring.jwt.service.VendorPartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendorPartServiceImpl implements VendorPartService {

    @Autowired
    private VendorPartRepository vendorPartRepository;

    @Override
    public VendorPartDto create(VendorPartDto vendorPartDto) {
        VendorPart vendorPart = mapToEntity(vendorPartDto);
        VendorPart saved = vendorPartRepository.save(vendorPart);
        return mapToDto(saved);
    }

    @Override
    public VendorPartDto update(Integer id, VendorPartDto dto) {
        VendorPart existing = vendorPartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("VendorPart not found with ID: " + id));

        existing.setSparePartId(dto.getSparePartId());
        existing.setPartName(dto.getPartName());
        existing.setDescription(dto.getDescription());
        existing.setManufacturer(dto.getManufacturer());
        existing.setPrice(dto.getPrice());
        existing.setUpdateAt(LocalDate.now());
        existing.setPartNumber(dto.getPartNumber());
        existing.setCGST(dto.getCGST());
        existing.setSGST(dto.getSGST());
        existing.setTotalGST(dto.getTotalGST());
        existing.setBuyingPrice(dto.getBuyingPrice());
        existing.setVendor(dto.getVendor());

        VendorPart updated = vendorPartRepository.save(existing);
        return mapToDto(updated);
    }


    @Override
    public VendorPartDto getById(Integer id) {
        VendorPart vendorPart = vendorPartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("VendorPart not found with id: " + id));
        return mapToDto(vendorPart);
    }

    @Override
    public List<VendorPartDto> getAll() {
        List<VendorPart> list = vendorPartRepository.findAll();
        return list.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        VendorPart vendorPart = vendorPartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("VendorPart not found with id: " + id));
        vendorPartRepository.delete(vendorPart);
    }

    @Override
    public List<VendorPartDto> getByPartNumber(String partNumber) {
        List<VendorPart> parts = vendorPartRepository.findByPartNumber(partNumber);
        if (parts.isEmpty()) {
            throw new ResourceNotFoundException("No VendorParts found with part number: " + partNumber);
        }
        return parts.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    // Helper methods
    private VendorPartDto mapToDto(VendorPart entity) {
        return VendorPartDto.builder()
                .vendorPartId(entity.getVendorPartId())
                .sparePartId(entity.getSparePartId())
                .partName(entity.getPartName())
                .description(entity.getDescription())
                .manufacturer(entity.getManufacturer())
                .price(entity.getPrice())
                .updateAt(entity.getUpdateAt())
                .partNumber(entity.getPartNumber())
                .cGST(entity.getCGST())
                .sGST(entity.getSGST())
                .totalGST(entity.getTotalGST())
                .buyingPrice(entity.getBuyingPrice())
                .vendor(entity.getVendor())
                .build();
    }

    private VendorPart mapToEntity(VendorPartDto dto) {
        return VendorPart.builder()
                .vendorPartId(dto.getVendorPartId()) // Only set this in update
                .sparePartId(dto.getSparePartId())
                .partName(dto.getPartName())
                .description(dto.getDescription())
                .manufacturer(dto.getManufacturer())
                .price(dto.getPrice())
                .updateAt(dto.getUpdateAt() != null ? dto.getUpdateAt() : LocalDate.now())
                .partNumber(dto.getPartNumber())
                .cGST(dto.getCGST())
                .sGST(dto.getSGST())
                .totalGST(dto.getTotalGST())
                .buyingPrice(dto.getBuyingPrice())
                .vendor(dto.getVendor())
                .build();
    }

}
