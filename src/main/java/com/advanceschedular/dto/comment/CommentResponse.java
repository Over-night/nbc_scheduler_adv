package com.advanceschedular.dto.comment;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommentResponse(
        Long id, UUID memberId, Long scheduleId, String nickname, String content,
        LocalDateTime createdAt, LocalDateTime updatedAt
) {
    public static CommentResponse of(
            Long id, UUID memberId, Long scheduleId, String nickname, String content,
            LocalDateTime createdAt, LocalDateTime updatedAt
    ) {
        return new CommentResponse(id, memberId, scheduleId, content, nickname, createdAt, updatedAt);
    }
}