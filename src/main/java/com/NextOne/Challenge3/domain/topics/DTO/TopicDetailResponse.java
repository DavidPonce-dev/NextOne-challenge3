package com.NextOne.Challenge3.domain.topics.DTO;

import com.NextOne.Challenge3.domain.topics.Topic;
import com.NextOne.Challenge3.domain.users.User;

import java.time.Instant;

public record TopicDetailResponse(
        Long id,
        String title,
        String content,
        Instant createdAt,
        String authorName
        ) {
    public TopicDetailResponse(Topic t) {
        this(
                t.getId(),
                t.getTitle(),
                t.getContent(),
                t.getCreatedAt(),
                t.getAuthor().getUsername()
        );
    }
}
