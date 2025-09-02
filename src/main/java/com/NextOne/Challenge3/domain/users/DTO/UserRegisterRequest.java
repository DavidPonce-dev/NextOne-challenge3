package com.NextOne.Challenge3.domain.users.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRegisterRequest(
        @NotBlank
        @Email
        String username,

        @NotBlank
        @Size(min = 8)
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$")
        String password
) {}
