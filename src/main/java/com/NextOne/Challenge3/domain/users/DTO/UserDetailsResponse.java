package com.NextOne.Challenge3.domain.users.DTO;

public record UserDetailsResponse(
        String username,
        String jwtToken
) {
}
