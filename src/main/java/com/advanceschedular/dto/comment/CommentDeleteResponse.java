package com.advanceschedular.dto.comment;

import java.time.Instant;
import java.util.UUID;

public record CommentDeleteResponse(Long id, Instant deletedAt) {
    public static CommentDeleteResponse of(Long id) {
        return new CommentDeleteResponse(id, Instant.now());
    }
}
