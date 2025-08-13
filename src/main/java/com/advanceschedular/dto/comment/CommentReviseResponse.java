package com.advanceschedular.dto.comment;

import java.time.Instant;
import java.util.UUID;

public record CommentReviseResponse(Long id, UUID memberId, Long scheduleId, Instant updatedAt) {
    public static CommentReviseResponse of(Long id, UUID memberId, Long scheduleId) {
        return new CommentReviseResponse(id, memberId, scheduleId, Instant.now());
    }
}
