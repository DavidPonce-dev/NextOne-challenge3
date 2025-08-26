package com.NextOne.Challenge3.domain.topics.DTO;

import com.NextOne.Challenge3.domain.topics.Topic;
import com.NextOne.Challenge3.domain.users.User;

import java.time.Instant;

public record TopicListResponse(
        Long id,
        String title,
        Instant createdAt,
        User author
) {
    public TopicListResponse(Topic t){
        this(
                t.getId(),
                t.getTitle(),
                t.getCreatedAt(),
                t.getAuthor()
        );
    }
}
