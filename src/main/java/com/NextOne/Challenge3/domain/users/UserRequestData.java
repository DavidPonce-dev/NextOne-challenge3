package com.NextOne.Challenge3.domain.users;

import jakarta.validation.constraints.NotBlank;

public record UserRequestData(
        @NotBlank String username,
        @NotBlank String password
) {}
