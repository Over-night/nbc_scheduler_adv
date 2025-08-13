package com.advanceschedular.controller;

import com.advanceschedular.common.enums.ErrorCode;
import com.advanceschedular.common.exception.CustomException;
import com.advanceschedular.dto.comment.*;
import com.advanceschedular.dto.schedule.*;
import com.advanceschedular.service.CommentService;
import com.advanceschedular.service.ScheduleService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/schedules")
@RequiredArgsConstructor
@Slf4j
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<ScheduleElementResponse>> getAllSchedules() {
        List<ScheduleElementResponse> allSchedules = scheduleService.getAllSchedules();
        return ResponseEntity.ok(allSchedules);
    }

    @PostMapping
    public ResponseEntity<ScheduleUploadResponse> uploadSchedule(
            HttpSession session,
            @RequestBody ScheduleUploadRequest dto
    ) {
        UUID memberUUID = (UUID) session.getAttribute("LOGIN_USER");
        if (memberUUID == null) {
            throw new CustomException(ErrorCode.USR_UNAUTHORIZED);
        }

        ScheduleUploadResponse response = scheduleService.uploadSchedule(memberUUID, dto);

        return ResponseEntity
                .created(URI.create("/api/v1/schedules/" + response.id()))
                .body(response);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleDetailResponse> getScheduleById(@PathVariable Long scheduleId) {
        ScheduleDetailResponse response = scheduleService.getScheduleDetail(scheduleId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{scheduleId}")
    public ResponseEntity<CommentUploadResponse> uploadComment(
            HttpSession session,
            @PathVariable Long scheduleId,
            @RequestBody CommentUploadRequest dto
    ) {
        UUID memberUUID = (UUID) session.getAttribute("LOGIN_USER");
        if (memberUUID == null) {
            throw new CustomException(ErrorCode.USR_UNAUTHORIZED);
        }

        CommentUploadResponse response = commentService.uploadComment(scheduleId, memberUUID, dto);
        return ResponseEntity
                .created(URI.create("/api/v1/schedules/" + response.scheduleId() + "/comments/" + response.id()))
                .body(response);
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleReviseResponse> reviseSchedule(
            HttpSession session,
            @PathVariable Long scheduleId,
            @RequestBody ScheduleReviseRequest dto
    ) {
        UUID memberUUID = (UUID) session.getAttribute("LOGIN_USER");
        if (memberUUID == null) {
            throw new CustomException(ErrorCode.USR_UNAUTHORIZED);
        }

        ScheduleReviseResponse response = scheduleService.reviseSchedule(scheduleId, memberUUID, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<ScheduleDeleteResponse> deleteSchedule(
            HttpSession session,
            @PathVariable Long scheduleId
    ) {
        UUID memberUUID = (UUID) session.getAttribute("LOGIN_USER");
        if (memberUUID == null) {
            throw new CustomException(ErrorCode.USR_UNAUTHORIZED);
        }

        ScheduleDeleteResponse response = scheduleService.deleteSchedule(scheduleId, memberUUID);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{scheduleId}/comments/{commentId}")
    public ResponseEntity<CommentReviseResponse> reviseComment(
            HttpSession session,
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @RequestBody CommentReviseRequest dto
    ) {
        UUID memberUUID = (UUID) session.getAttribute("LOGIN_USER");
        if (memberUUID == null) {
            throw new CustomException(ErrorCode.USR_UNAUTHORIZED);
        }

        CommentReviseResponse response = commentService.reviseComment(commentId, memberUUID, dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{scheduleId}/comments/{commentId}")
    public ResponseEntity<CommentDeleteResponse> deleteComment(
            HttpSession session,
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @RequestBody CommentReviseRequest dto
    ) {
        UUID memberUUID = (UUID) session.getAttribute("LOGIN_USER");
        if (memberUUID == null) {
            throw new CustomException(ErrorCode.USR_UNAUTHORIZED);
        }

        CommentDeleteResponse response = commentService.deleteComment(commentId, memberUUID);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/member/{memberUUID}")
    public ResponseEntity<List<ScheduleElementResponse>> getSchedulesByUsername(@PathVariable UUID memberUUID) {
        List<ScheduleElementResponse> schedulesByUUID = scheduleService.getSchedulesByUUID(memberUUID);
        return ResponseEntity.ok(schedulesByUUID);
    }
}
