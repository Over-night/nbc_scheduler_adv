package com.advanceschedular.dto.schedule;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;


public record ScheduleReviseResponse(Long id, UUID memberId, Instant updatedAt) {
    public static ScheduleReviseResponse of(Long id, UUID memberId) {
        return new ScheduleReviseResponse(id, memberId, Instant.now());
    }
}