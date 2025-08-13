package com.advanceschedular.dto.member;

import java.time.LocalDateTime;
import java.util.UUID;

public record MemberResponse (
        UUID id, String username, String nickname, String email,
        LocalDateTime createdAt, LocalDateTime updatedAt
) {
    public static MemberResponse of(
            UUID id, String username, String nickname, String email,
            LocalDateTime createdAt, LocalDateTime updatedAt
    ) {
        return new MemberResponse(id, username, nickname, email, createdAt, updatedAt);
    }
}
