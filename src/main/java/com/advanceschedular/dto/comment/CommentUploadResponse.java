package com.advanceschedular.dto.comment;

import com.advanceschedular.dto.schedule.ScheduleUploadResponse;

import java.time.Instant;
import java.util.UUID;

public record CommentUploadResponse(Long id, UUID memberId, Long scheduleId, Instant createdAt) {
    public static CommentUploadResponse of(Long id, UUID memberId, Long scheduleId) {
        return new CommentUploadResponse(id, memberId, scheduleId, Instant.now());
    }
}
