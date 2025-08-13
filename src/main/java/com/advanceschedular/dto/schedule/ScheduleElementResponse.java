package com.advanceschedular.dto.schedule;

import java.time.LocalDateTime;
import java.util.UUID;

public record ScheduleElementResponse (
        Long id, UUID memberId, String title, String nickname, String content,
        LocalDateTime createdAt, LocalDateTime updatedAt
) {
    public static ScheduleElementResponse of(
            Long id, UUID memberId, String title, String nickname, String content,
            LocalDateTime createdAt, LocalDateTime updatedAt
    ) {
        return new ScheduleElementResponse(id, memberId, title, nickname, content, createdAt, updatedAt);
    }
}