package com.NextOne.Challenge3.domain.topics.DTO;

import jakarta.validation.constraints.NotNull;

public record TopicUpdateRequest(
        @NotNull Long id,
        String title,
        String content) {
}
