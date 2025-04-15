package com.spring.jwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ResetPassword {

    @Schema(
            description = "Token Used to Reset Password", example = "FMfcgzQZTMQRvrFrnvXhzrdmXQzShWXq"
    )
    private  String token;

    @Schema(
            description = "Password Entered for Resetting ", example = "Pass@1234"
    )
    private String password;

    @Schema(
            description = "Match it with Password Entered for Resetting ", example = "Pass@1234"
    )
    private String confirmPassword;

}
