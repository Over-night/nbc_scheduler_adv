package com.advanceschedular.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class MemberSignUpResponse {
    private final UUID id;
    private final String nickname;
    private final Instant createdAt;

    public static MemberSignUpResponse created(UUID id, String nickname) {
        return MemberSignUpResponse.builder()
                .id(id)
                .nickname(nickname)
                .createdAt(Instant.now())
                .build();
    }
}

