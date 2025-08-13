package com.advanceschedular.dto.schedule;


import java.time.Instant;


public record ScheduleDeleteResponse(Long id, Instant deletedAt) {
    public static ScheduleDeleteResponse of(Long id) {
        return new ScheduleDeleteResponse(id, Instant.now());
    }
}