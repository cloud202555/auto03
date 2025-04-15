package com.spring.jwt.SavePart;

import com.spring.jwt.Appointment.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/save-part")
public class SavePartController {

    @Autowired
    private SavePartService savePartService;

    @PostMapping("/save")
    public ResponseDto<String> savePart(@RequestBody SavePartDto savePartDto) {
        try {
            String message = savePartService.savePart(savePartDto);
            return ResponseDto.success("Spare Part saved successfully", message);
        } catch (Exception e) {
            return ResponseDto.error("Failed to save Spare Part", e.getMessage());
        }
    }

    @GetMapping("/get")
    public ResponseDto<SavePart> getSavedPartById(@RequestParam Integer savePartId) {
        try {
            SavePart savedPart = savePartService.getSavedPartById(savePartId);
            return ResponseDto.success("Saved Part found", savedPart);
        } catch (Exception e) {
            return ResponseDto.error("Failed to fetch Saved Part", e.getMessage());
        }
    }

    @GetMapping("/user")
    public ResponseDto<List<SavePartDto>> getSavedPartsByUser(@RequestParam int userId) {
        try {
            List<SavePartDto> savedParts = savePartService.getSavedPart(userId);
            return ResponseDto.success("Saved Parts retrieved", savedParts);
        } catch (Exception e) {
            return ResponseDto.error("Failed to fetch Saved Parts", e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseDto<String> deleteSavedPart(@RequestParam Integer savePartId) {
        try {
            String message = savePartService.deleteSavedParById(savePartId);
            return ResponseDto.success("Deleted Successfully", message);
        } catch (Exception e) {
            return ResponseDto.error("Failed to delete Saved Part", e.getMessage());
        }
    }

    @GetMapping("/find")
    public ResponseDto<SavePart> getByPartAndUserId(@RequestParam int userId, @RequestParam Integer sparePartId) {
        try {
            SavePart savePart = savePartService.getByPartAndUserId(userId, sparePartId);
            return ResponseDto.success("Saved Part found", savePart);
        } catch (Exception e) {
            return ResponseDto.error("Failed to fetch Saved Part", e.getMessage());
        }
    }
}
