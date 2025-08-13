package com.advanceschedular.dto.schedule;


import com.advanceschedular.dto.comment.CommentResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ScheduleDetailResponse (
        Long id, UUID memberId, String title, String nickname, String content,
        LocalDateTime createdAt, LocalDateTime updatedAt, List<CommentResponse> comments
) {
    public static ScheduleDetailResponse of(
            Long id, UUID memberId, String title, String nickname, String content,
            LocalDateTime createdAt, LocalDateTime updatedAt, List<CommentResponse> comments
    ) {
        return new ScheduleDetailResponse(id, memberId, title, nickname, content, createdAt, updatedAt, comments);
    }
}