package com.spring.jwt.SparePart;

import com.spring.jwt.utils.BaseResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SparePartService {
    public BaseResponseDTO addPart(String partName, String description, String manufacturer, Long price, String partNumber, List<MultipartFile> photos,Integer sGST,Integer cGST,Integer totalGST,Integer buyingPrice,String make, String vendor);

//    byte[] getPhotoById(Integer id);

    SparePartDto getSparePartById(Integer id);

    public Page<SparePartDto> getAllSpareParts(int page, int size);

    public SparePartDto updatePart(Integer id, String partName, String description, String manufacturer, Long price, String partNumber, List<MultipartFile> photos,Integer sGST,Integer cGST,Integer totalGST,Integer buyingPrice,String make, String vendor);

    public BaseResponseDTO deleteSparePartById(Integer id, Integer photoIndex);

}
