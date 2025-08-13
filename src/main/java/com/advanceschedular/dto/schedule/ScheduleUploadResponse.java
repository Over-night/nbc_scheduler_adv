package com.advanceschedular.dto.schedule;

import java.time.Instant;
import java.util.UUID;

public record ScheduleUploadResponse(Long id, UUID memberId, Instant createdAt) {
    public static ScheduleUploadResponse of(Long id, UUID memberId) {
        return new ScheduleUploadResponse(id, memberId, Instant.now());
    }
}