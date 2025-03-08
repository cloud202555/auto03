package com.spring.jwt.controller;

import com.spring.jwt.dto.ResetPassword;
import com.spring.jwt.dto.ResponseAllUsersDto;
import com.spring.jwt.dto.UserDTO;
import com.spring.jwt.dto.UserUpdateRequest;
import com.spring.jwt.exception.PageNotFoundException;
import com.spring.jwt.exception.UserNotFoundExceptions;
import com.spring.jwt.service.UserService;
import com.spring.jwt.utils.BaseResponseDTO;
import com.spring.jwt.utils.ErrorResponseDto;
import com.spring.jwt.utils.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


@Tag(
        name = "CRUD REST APIs for User in AutoCarCare ",
        description = "CRUD REST APIs in AutoCarCare  to CREATE, UPDATE, FETCH AND DELETE USER Details"
)

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Create User Account REST API",
            description = "REST API to create new Card inside EazyBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Mobile Number is already registered Or Email Number is already registered",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Email Id Not Verified",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )

    @PostMapping("/registerUser")
    public ResponseEntity<BaseResponseDTO> register(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.registerAccount(userDTO));
    }

    @Operation(
            summary = "Forgot Password REST API",
            description = "REST API to Reset Password inside AutoCarCare"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OTP sent successfully."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Email field is empty",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )

    @PostMapping("/forgot-password")
    public ResponseEntity<ResponseDto> forgotPassword(HttpServletRequest request) {
        String email = request.getParameter("email");
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ResponseDto("Unsuccessful", "Email is required"));
        }

        String token = RandomStringUtils.randomAlphabetic(40);
        userService.updateResetPassword(token, email);

        String resetPasswordLink = "http://localhost:5173/user/reset-password?token=" + token;
        ResponseDto response = userService.forgotPass(email, resetPasswordLink, request.getServerName());

        return ResponseEntity.ok(new ResponseDto("Successful", response.getMessage()));
    }

    @PostMapping("/update-password")
    public ResponseEntity<ResponseDto> updatePassword(@RequestBody ResetPassword request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity.badRequest()
                    .body(new ResponseDto("Unsuccessful", "Passwords not matching"));
        }

        if (!userService.validateResetToken(request.getToken())) {
            return ResponseEntity.badRequest()
                    .body(new ResponseDto("Unsuccessful", "Invalid or expired token"));
        }

        if (userService.isSameAsOldPassword(request.getToken(), request.getPassword())) {
            return ResponseEntity.badRequest()
                    .body(new ResponseDto("Unsuccessful", "New password cannot be the same as the old password"));
        }

        ResponseDto response = userService.updatePassword(request.getToken(), request.getPassword());
        return ResponseEntity.ok(new ResponseDto("Successful", response.getMessage()));
    }


    @Operation(
            summary = "GetAllUsers REST API",
            description = "REST API to Fetch All Users List inside AutoCarCare"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )

    @GetMapping("/getAllUsers")
    public ResponseEntity<ResponseAllUsersDto> getAllUsers(@RequestParam(defaultValue= "0") int pageNo, @RequestParam(defaultValue= "10") int pageSize) {
        try {
            Page<UserDTO> userPage = userService.getAllUsers(pageNo, pageSize);
            ResponseAllUsersDto response = new ResponseAllUsersDto("success", userPage.getContent());
            response.setTotalPages(userPage.getTotalPages());
            response.setPageSize(userPage.getSize());
            return ResponseEntity.ok(response);
        } catch (UserNotFoundExceptions | PageNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseAllUsersDto(e.getMessage()));
        }
    }

    @Operation(
            summary = "Get User Details REST API",
            description = "REST API to Fetch Single User Details inside AutoCarCare"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User Not Found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )

    @GetMapping("getUser/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(
            summary = "Update User Details REST API",
            description = "REST API to Update User Details List inside AutoCarCare"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User Not Found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )

        @PatchMapping("updateDetails/{id}")
        public ResponseEntity<UserDTO> updateUser(
                @PathVariable Long id, @RequestBody UserUpdateRequest request) {
            return ResponseEntity.ok(userService.updateUser(id, request));
        }
    }




