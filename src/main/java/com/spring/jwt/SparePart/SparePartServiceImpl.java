package com.spring.jwt.SparePart;

import com.spring.jwt.MapperClasses.SparePartMappers;
import com.spring.jwt.VehicleReg.BadRequestException;
import com.spring.jwt.exception.SparePartNotFoundException;
import com.spring.jwt.utils.BaseResponseDTO;
import com.spring.jwt.utils.ImageCompressionUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SparePartServiceImpl implements SparePartService {

    public final SparePartRepo sparePartRepo;

    public final SparePartMapper sparePartMapper;

    public static final Logger logger = LoggerFactory.getLogger(SparePartServiceImpl.class);

    @Override
    public BaseResponseDTO addPart(String partName, String description, String manufacturer, Long price, Long partNumber, List<MultipartFile> photos) {
        try {
            List<byte[]> compressedPhotos = photos.stream()
                    .map(file -> {
                        try {
                            return ImageCompressionUtil.compressImage(file.getBytes());
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to compress image", e);
                        }
                    })
                    .toList();

            SparePart sparePart = SparePart.builder()
                    .partName(partName)
                    .description(description)
                    .manufacturer(manufacturer)
                    .price(price)
                    .partNumber(partNumber)
                    .photo(compressedPhotos)
                    .updateAt(LocalDate.now())
                    .build();

            sparePartRepo.save(sparePart);
            return new BaseResponseDTO("Success", "Part Added Successfully");

        } catch (DataIntegrityViolationException e) {
            logger.error("Duplicate part number error: ", e);
            throw new BadRequestException("Part number " + partNumber + " already exists.");
        } catch (RuntimeException e) {
            logger.error("Error processing images: ", e);
            throw new BadRequestException("Failed to process images");
        }
    }

    @Override
    public SparePartDto getSparePartById(Integer id) {
        Optional<SparePart> sparePartOptional = sparePartRepo.findById(id);

        return sparePartOptional.map(sparePart -> SparePartDto.builder()
                .sparePartId(sparePart.getSparePartId())
                .partName(sparePart.getPartName())
                .description(sparePart.getDescription())
                .manufacturer(sparePart.getManufacturer())
                .price(sparePart.getPrice())
                .partNumber(sparePart.getPartNumber())
                .updateAt(sparePart.getUpdateAt())
                .photo(sparePart.getPhoto().stream()
                        .map(photo -> Base64.getEncoder().encodeToString(photo))
                        .toList())
                .build()
        ).orElse(null);
    }

    @Override
    public List<SparePartDto> getAllSpareParts() {
        List<SparePart> spareParts = sparePartRepo.findAll();
        return spareParts.stream()
                .map(SparePartMappers::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SparePartDto updatePart(Integer id, String partName, String description, String manufacturer, Long price, Long partNumber, List<MultipartFile> photos) {
        SparePart sparePart = sparePartRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Spare part not found"));

        Optional.ofNullable(partName).ifPresent(sparePart::setPartName);
        Optional.ofNullable(description).ifPresent(sparePart::setDescription);
        Optional.ofNullable(manufacturer).ifPresent(sparePart::setManufacturer);
        Optional.ofNullable(price).ifPresent(sparePart::setPrice);
        Optional.ofNullable(partNumber).ifPresent(sparePart::setPartNumber);

        if (photos != null && !photos.isEmpty()) {
            try {
                // Compress new photos
                List<byte[]> compressedPhotos = photos.stream()
                        .map(file -> {
                            try {
                                byte[] compressed = ImageCompressionUtil.compressImage(file.getBytes());
                                if (compressed == null || compressed.length == 0) {
                                    logger.error("Compressed image is empty for file: {}", file.getOriginalFilename());
                                    throw new RuntimeException("Compressed image is empty");
                                }
                                return compressed;
                            } catch (IOException e) {
                                logger.error("Failed to compress image: {}", file.getOriginalFilename(), e);
                                throw new RuntimeException("Failed to compress image", e);
                            }
                        })
                        .collect(Collectors.toList());

                logger.info("Number of compressed photos: {}", compressedPhotos.size());


                List<byte[]> existingPhotos = sparePart.getPhoto();
                if (existingPhotos == null) {
                    existingPhotos = new ArrayList<>();
                }
                existingPhotos.addAll(compressedPhotos);
                sparePart.setPhoto(existingPhotos);
            } catch (Exception e) {
                logger.error("Failed to upload images", e);
                throw new RuntimeException("Failed to upload images", e);
            }
        } else {
            logger.warn("No photos provided for update. Skipping photo update.");
        }

        SparePart updatedPart = sparePartRepo.save(sparePart);

        logger.info("Updated spare part: {}", updatedPart);

        return sparePartMapper.toDto(updatedPart);
    }
    @Override
    public BaseResponseDTO deleteSparePartById(Integer id, Integer photoIndex) {
        return sparePartRepo.findById(id)
                .map(sparePart -> {
                    if (photoIndex != null) {

                        List<byte[]> photos = sparePart.getPhoto();
                        if (photoIndex >= 0 && photoIndex < photos.size()) {
                            photos.remove(photoIndex.intValue());
                            sparePart.setPhoto(photos);
                            sparePartRepo.save(sparePart);
                            return BaseResponseDTO.builder()
                                    .code("SUCCESS")
                                    .message("Photo deleted successfully")
                                    .build();
                        } else {
                            throw new IllegalArgumentException("Invalid photo index: " + photoIndex);
                        }
                    } else {
                        sparePartRepo.deleteById(id);
                        return BaseResponseDTO.builder()
                                .code("SUCCESS")
                                .message("Spare part deleted successfully")
                                .build();
                    }
                })
                .orElseThrow(() -> new SparePartNotFoundException("Not found with ID: " + id));
    }



}

