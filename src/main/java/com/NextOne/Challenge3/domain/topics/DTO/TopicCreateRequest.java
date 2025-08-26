package com.NextOne.Challenge3.domain.topics.DTO;

import jakarta.validation.constraints.NotBlank;

public record TopicCreateRequest(
        @NotBlank String title,
        @NotBlank String content
) {
}
