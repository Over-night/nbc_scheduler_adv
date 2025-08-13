package com.advanceschedular.dto.member;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record MemberSignUpResponse(UUID id, String nickname, Instant createdAt) {
    public static MemberSignUpResponse created(UUID id, String nickname) {
        return MemberSignUpResponse.builder()
                .id(id)
                .nickname(nickname)
                .createdAt(Instant.now())
                .build();
    }
}

