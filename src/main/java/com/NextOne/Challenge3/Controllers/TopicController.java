package com.NextOne.Challenge3.Controllers;

import com.NextOne.Challenge3.domain.topics.*;
import com.NextOne.Challenge3.domain.topics.DTO.TopicCreateRequest;
import com.NextOne.Challenge3.domain.topics.DTO.TopicDetailResponse;
import com.NextOne.Challenge3.domain.topics.DTO.TopicListResponse;
import com.NextOne.Challenge3.domain.topics.DTO.TopicUpdateRequest;
import com.NextOne.Challenge3.domain.users.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topics")
@RequiredArgsConstructor
public class TopicController {

    private final TopicRepository topicRepository;

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<Page<TopicListResponse>> list (
            @PageableDefault(
                    size = 10,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        Page<TopicListResponse> topics = topicRepository.findByStatusTrue(pageable)
                .map(TopicListResponse::new);
        return ResponseEntity.ok().body(topics);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<TopicDetailResponse> getDetail (@PathVariable Long id) {
        Topic topic = topicRepository.findById(id)
                .filter(Topic::isStatus)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found"));
        return ResponseEntity.ok(new TopicDetailResponse(topic));
    }

    @PostMapping
    public ResponseEntity<TopicDetailResponse> create (
            @RequestBody @Valid TopicCreateRequest topicCreateRequest,
            @AuthenticationPrincipal User user,
            UriComponentsBuilder uriComponentsBuilder
    ){
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication required");
        }
        Topic topic = topicRepository.save(new Topic(topicCreateRequest, user));
        URI url = uriComponentsBuilder.path("/topics/{id}").buildAndExpand(topic.getId()).toUri();
        return ResponseEntity.created(url).body(new TopicDetailResponse(topic));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicDetailResponse> update (
            @PathVariable Long id,
            @RequestBody @Valid TopicUpdateRequest topicUpdateRequest,
            @AuthenticationPrincipal User user
    ){
        Topic topic = topicRepository
                .findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topico no encontrado"));

        if (!topic.getAuthor().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permitido editar este topico");
        }

        topic.updateTopic(topicUpdateRequest);
        return ResponseEntity.ok().body(new TopicDetailResponse(topic));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete (
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ){
        Topic topic = topicRepository
                .findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topico no encontrado"));

        if (!topic.getAuthor().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permitido borrar este topico");
        }

        topic.disable();
        return ResponseEntity.noContent().build();
    }
}
