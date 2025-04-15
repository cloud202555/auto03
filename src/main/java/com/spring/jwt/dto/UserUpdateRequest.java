package com.spring.jwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {
    @Schema(
            description = "Email of User", example = "example@example.com"
    )
    private String email;

    @Schema(
            description = "Address of the customer", example = "A/P Pune Main Street Block no 8"
    )
    private String Address;

    @Schema(
            description = "First Name of the customer", example = "John"
    )
    private String firstName;
    @Schema(
            description = "Last Name of the customer", example = "Doe"
    )
    private String lastName;

    @Schema(
            description = "Mobile Number of the customer", example = "9822222212"
    )
    private Long mobileNumber;
}

