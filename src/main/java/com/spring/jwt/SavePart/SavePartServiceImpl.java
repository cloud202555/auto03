package com.spring.jwt.SavePart;

import com.spring.jwt.SparePart.SparePartRepo;
import com.spring.jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SavePartServiceImpl implements SavePartService {

    @Autowired
    private SavePartRepository savePartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SparePartRepo sparePartRepository;

    public String savePart(SavePartDto savePartDto) {
        if (savePartDto.getUserId() == null || savePartDto.getUserId() <= 0) {
            throw new IllegalArgumentException("Invalid User ID");
        }

        if (savePartDto.getSparePartId() == null || savePartDto.getSparePartId() <= 0) {
            throw new IllegalArgumentException("Invalid Spare Part ID");
        }

        // Check if user exists
        boolean userExists = userRepository.existsById(Long.valueOf(savePartDto.getUserId()));
        boolean sparePartExists = sparePartRepository.existsById(savePartDto.getSparePartId());

        if (!userExists && !sparePartExists) {
            throw new RuntimeException("User ID " + savePartDto.getUserId() + " and Spare Part ID " + savePartDto.getSparePartId() + " not found.");
        } else if (!userExists) {
            throw new RuntimeException("User not found with ID: " + savePartDto.getUserId());
        } else if (!sparePartExists) {
            throw new RuntimeException("Spare part not found with ID: " + savePartDto.getSparePartId());
        }

        // Check if the same userId and sparePartId already exist
        boolean alreadyExists = savePartRepository.existsByUserIdAndSparePartId(savePartDto.getUserId(), savePartDto.getSparePartId());
        if (alreadyExists) {
            throw new RuntimeException("Spare Part with ID " + savePartDto.getSparePartId() + " is already saved for User ID " + savePartDto.getUserId());
        }

        SavePart savePart = new SavePart(savePartDto);
        savePartRepository.save(savePart);
        return "Spare Part saved successfully";
    }



    @Override
    public SavePart getSavedPartById(Integer savePartId) {
        return savePartRepository.findById(savePartId)
                .orElseThrow(() -> new RuntimeException("Saved Part not found with ID: " + savePartId));
    }

    @Override
    public List<SavePartDto> getSavedPart(int userId) {
        List<SavePart> savedParts = savePartRepository.findByUserId(userId);

        if (savedParts.isEmpty()) {
            throw new RuntimeException("No saved parts found for user ID: " + userId);
        }

        return savedParts.stream()
                .map(SavePartDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public String deleteSavedParById(Integer savePartId) {
        SavePart savePart = savePartRepository.findById(savePartId)
                .orElseThrow(() -> new RuntimeException("Saved Part not found with ID: " + savePartId));

        savePartRepository.delete(savePart);
        return "Saved Part deleted successfully";
    }

    @Override
    public SavePart getByPartAndUserId(int userId, Integer sparePartId) {
        return savePartRepository.findByUserIdAndSparePartId(userId, sparePartId)
                .orElseThrow(() -> new RuntimeException("No saved part found for user ID: " + userId + " and spare part ID: " + sparePartId));
    }
}
