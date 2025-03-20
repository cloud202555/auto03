package com.spring.jwt.UserParts;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/userParts")
public class UserPartController {

    private final UserPartService userPartService;

    public UserPartController(UserPartService userPartService) {
        this.userPartService = userPartService;
    }

    @GetMapping("/getById")
    public ResponseEntity<?> getUserPartById(@RequestParam Integer userPartId) {
        try {
            UserPartDto userPart = userPartService.getUserPartById(userPartId);
            return ResponseEntity.ok(userPart);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("UserPart not found: " + e.getMessage());
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllUserParts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<UserPartDto> userParts = userPartService.getAllUserParts(page, size);
            return ResponseEntity.ok(userParts);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to retrieve user parts: " + e.getMessage());
        }
    }


}

