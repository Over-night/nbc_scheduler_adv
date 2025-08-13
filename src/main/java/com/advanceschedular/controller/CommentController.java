package com.advanceschedular.controller;

import com.advanceschedular.dto.comment.CommentResponse;
import com.advanceschedular.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getAllComments() {
        List<CommentResponse> allComments = commentService.getAllComments();
        return ResponseEntity.ok(allComments);
    }

    @GetMapping("/member/{memberUUID}")
    public ResponseEntity<List<CommentResponse>> getCommentsByMemberUUID(@PathVariable UUID memberUUID) {
        List<CommentResponse> commentsByUUID = commentService.getCommentsByMemberUUID(memberUUID);
        return ResponseEntity.ok(commentsByUUID);
    }
}
