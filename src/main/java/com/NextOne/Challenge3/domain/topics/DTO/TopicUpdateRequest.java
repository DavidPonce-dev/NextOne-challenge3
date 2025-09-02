package com.NextOne.Challenge3.domain.topics.DTO;

import jakarta.validation.constraints.NotBlank;

public record TopicUpdateRequest(
        String title,
        String content
) {
}
