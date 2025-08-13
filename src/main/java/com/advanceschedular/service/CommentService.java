package com.advanceschedular.service;

import com.advanceschedular.common.enums.ErrorCode;
import com.advanceschedular.common.exception.CustomException;
import com.advanceschedular.dto.comment.*;
import com.advanceschedular.dto.schedule.ScheduleElementResponse;
import com.advanceschedular.model.Comment;
import com.advanceschedular.model.Member;
import com.advanceschedular.model.Schedule;
import com.advanceschedular.repository.CommentRepository;
import com.advanceschedular.repository.MemberRepository;
import com.advanceschedular.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    private CommentResponse makeCommentResponse(Comment comment) {
        return CommentResponse.of(
                comment.getId(), comment.getMember().getId(), comment.getSchedule().getId(),
                comment.getMember().getNickname(), comment.getContent(),
                comment.getCreatedAt(), comment.getUpdatedAt()
        );
    }

    private Member getMember(UUID id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.CMT_INVALID_WRITER));
    }

    private Schedule getSchedule(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.CMT_INVALID_SCHEDULE));
    }

    private Comment getComment(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.CMT_INVALID_COMMENT));
    }

    @Transactional
    public CommentUploadResponse uploadComment(Long id, UUID memberId, CommentUploadRequest dto) {
        Member member = getMember(memberId);
        Schedule schedule = getSchedule(id);

        if(schedule.isDeleted()){
            throw new CustomException(ErrorCode.CMT_DELETED_SCHEDULE);
        }

        Comment comment = Comment.builder().
                member(member).
                schedule(schedule).
                content(dto.getContent()).
                build();

        Comment savedComment = commentRepository.save(comment);

        return CommentUploadResponse.of(savedComment.getId(), savedComment.getMember().getId(), savedComment.getSchedule().getId());
    }

    @Transactional
    public CommentReviseResponse reviseComment(Long id, UUID memberId, CommentReviseRequest dto) {
        Member member = getMember(memberId);
        Comment comment = getComment(id);

        if (comment.isDeleted()) {
            throw new CustomException(ErrorCode.CMT_ALREADY_DELETED);
        }
        if (comment.getSchedule().isDeleted()) {
            throw new CustomException(ErrorCode.CMT_DELETED_SCHEDULE);
        }
        if (!comment.getMember().getId().equals(member.getId())) {
            throw new CustomException(ErrorCode.CMT_NO_AUTHORITY_TO_REVISE);
        }

        comment.update(dto.getContent());

        return CommentReviseResponse.of(comment.getId(), comment.getMember().getId(), comment.getSchedule().getId());
    }

    @Transactional
    public CommentDeleteResponse deleteComment(Long id, UUID memberId) {
        Member member = getMember(memberId);
        Comment comment = getComment(id);

        if (comment.isDeleted()) {
            throw new CustomException(ErrorCode.CMT_ALREADY_DELETED);
        }
        if (comment.getSchedule().isDeleted()) {
            throw new CustomException(ErrorCode.CMT_DELETED_SCHEDULE);
        }
        if (!comment.getMember().getId().equals(member.getId())) {
            throw new CustomException(ErrorCode.CMT_NO_AUTHORITY_TO_DELETE);
        }

        comment.delete();

        return CommentDeleteResponse.of(comment.getId());
    }

    public List<CommentResponse> getAllComments() {
        return commentRepository.findAll()
                .stream()
                .filter(comment -> !comment.isDeleted())
                .map(this::makeCommentResponse)
                .collect(Collectors.toList());
    }

    public CommentResponse getCommentById(Long id) {
        Comment comment = getComment(id);

        if (comment.isDeleted()) {
            throw new CustomException(ErrorCode.CMT_ALREADY_DELETED);
        }

        return makeCommentResponse(comment);
    }

    public List<CommentResponse> getCommentsByMemberUUID(UUID id) {
        Member owner = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USR_INVALID_USER_UUID));

        return commentRepository.findByMember(owner)
                .stream()
                .filter(comment -> !comment.isDeleted())
                .map(this::makeCommentResponse)
                .collect(Collectors.toList());
    }

    public List<CommentResponse> getCommentsByScheduleId(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(IllegalStateException::new);

        return commentRepository.findBySchedule(schedule)
                .stream()
                .filter(comment -> !comment.isDeleted())
                .map(this::makeCommentResponse)
                .collect(Collectors.toList());
    }

    public List<CommentResponse> getCommentsByMemberUUIDIncludingDeleted(UUID id) {
        Member owner = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USR_INVALID_USER_UUID));

        return commentRepository.findByMember(owner)
                .stream()
                .map(this::makeCommentResponse)
                .collect(Collectors.toList());
    }

    public List<CommentResponse> getCommentsByScheduleIdIncludingDeleted(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(IllegalStateException::new);

        return commentRepository.findBySchedule(schedule)
                .stream()
                .map(this::makeCommentResponse)
                .collect(Collectors.toList());
    }
}
