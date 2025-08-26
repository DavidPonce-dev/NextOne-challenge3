package com.NextOne.Challenge3.domain.topics;

import com.NextOne.Challenge3.domain.topics.DTO.TopicCreateRequest;
import com.NextOne.Challenge3.domain.topics.DTO.TopicUpdateRequest;
import com.NextOne.Challenge3.domain.users.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "topics")
@Entity(name = "Topic")
@EqualsAndHashCode(of = "id")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private boolean status = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    public Topic(TopicCreateRequest req, User user) {
        this.title = req.title();
        this.content = req.content();
        this.author = user;
    }

    public void updateTopic(TopicUpdateRequest t) {
        if (t.title() != null) this.title = t.title();
        if (t.content() != null) this.content = t.content();
    }

    public void disable() {
        this.status = false;
    }
}
