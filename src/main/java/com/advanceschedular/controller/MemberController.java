package com.advanceschedular.controller;

import com.advanceschedular.common.enums.ErrorCode;
import com.advanceschedular.common.exception.CustomException;
import com.advanceschedular.dto.comment.CommentResponse;
import com.advanceschedular.dto.member.MemberResponse;
import com.advanceschedular.dto.schedule.ScheduleElementResponse;
import com.advanceschedular.model.Schedule;
import com.advanceschedular.service.CommentService;
import com.advanceschedular.service.MemberService;
import com.advanceschedular.service.ScheduleService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final ScheduleService scheduleService;
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<MemberResponse>> getAllUsers() {
        List<MemberResponse> allUsers = memberService.getAllMembers();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> getMyInfo(
            HttpSession session
    ) {
        UUID memberUUID = (UUID) session.getAttribute("LOGIN_USER");
        if (memberUUID == null) {
            throw new CustomException(ErrorCode.USR_UNAUTHORIZED);
        }

        MemberResponse memberByMe = memberService.getMemberByUUID(memberUUID);
        return ResponseEntity.ok(memberByMe);
    }

    @GetMapping("/me/schedules")
    public ResponseEntity<List<ScheduleElementResponse>> getSchedulesByMe(
            HttpSession session
    ) {
        UUID memberUUID = (UUID) session.getAttribute("LOGIN_USER");
        if (memberUUID == null) {
            throw new CustomException(ErrorCode.USR_UNAUTHORIZED);
        }

        List<ScheduleElementResponse> schedulesByMe = scheduleService.getSchedulesByUUID(memberUUID);
        return ResponseEntity.ok(schedulesByMe);
    }

    @GetMapping("/me/comments")
    public ResponseEntity<List<CommentResponse>> getCommentsByMe(
            HttpSession session
    ) {
        UUID memberUUID = (UUID) session.getAttribute("LOGIN_USER");
        if (memberUUID == null) {
            throw new CustomException(ErrorCode.USR_UNAUTHORIZED);
        }

        List<CommentResponse> commentsByMe = commentService.getCommentsByMemberUUID(memberUUID);
        return ResponseEntity.ok(commentsByMe);
    }

    @GetMapping("/search/{memberUUID}")
    public ResponseEntity<MemberResponse> getMemberByUUID(@PathVariable UUID memberUUID) {
        MemberResponse memberByUUID = memberService.getMemberByUUID(memberUUID);
        return ResponseEntity.ok(memberByUUID);
    }

    @GetMapping("/search/{memberUUID}/schedules")
    public ResponseEntity<List<ScheduleElementResponse>> getSchedulesByUUID(@PathVariable UUID memberUUID) {
        List<ScheduleElementResponse> schedulesByUUID = scheduleService.getSchedulesByUUID(memberUUID);
        return ResponseEntity.ok(schedulesByUUID);
    }

    @GetMapping("/search/{memberUUID}/comments")
    public ResponseEntity<List<CommentResponse>> getCommentsByUUID(@PathVariable UUID memberUUID) {
        List<CommentResponse> commentsByUUID = commentService.getCommentsByMemberUUID(memberUUID);
        return ResponseEntity.ok(commentsByUUID);
    }
}
