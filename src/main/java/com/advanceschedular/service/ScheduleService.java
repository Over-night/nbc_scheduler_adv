package com.advanceschedular.service;

import com.advanceschedular.common.enums.ErrorCode;
import com.advanceschedular.common.exception.CustomException;
import com.advanceschedular.dto.comment.CommentResponse;
import com.advanceschedular.dto.schedule.*;
import com.advanceschedular.model.Member;
import com.advanceschedular.model.Schedule;
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
public class ScheduleService {
    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;

    private ScheduleElementResponse makeScheduleElementResponse(Schedule schedule) {
        return ScheduleElementResponse.of(
                schedule.getId(), schedule.getMember().getId(),
                schedule.getTitle(), schedule.getMember().getNickname(), schedule.getContent(),
                schedule.getCreatedAt(), schedule.getUpdatedAt()
        );
    }

    @Transactional
    public ScheduleUploadResponse uploadSchedule(UUID memberId, ScheduleUploadRequest dto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_WRITER));

        Schedule schedule = Schedule.builder().
                member(member).
                title(dto.getTitle()).
                content(dto.getContent()).
                build();

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return ScheduleUploadResponse.of(savedSchedule.getId(), savedSchedule.getMember().getId());
    }

    @Transactional
    public ScheduleReviseResponse reviseSchedule(Long scheduleId, UUID memberId, ScheduleReviseRequest dto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_WRITER));
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_SCHEDULE));

        if (schedule.isDeleted()) {
            throw new CustomException(ErrorCode.ALREADY_DELETED);
        }
        if (!schedule.getMember().getId().equals(member.getId())) {
            throw new CustomException(ErrorCode.NO_AUTHORITY_TO_REVISE);
        }

        schedule.update(dto.getTitle(), dto.getContent());

        return ScheduleReviseResponse.of(schedule.getId(), schedule.getMember().getId());
    }

    @Transactional
    public ScheduleDeleteResponse deleteSchedule(Long scheduleId, UUID memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_WRITER));
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_SCHEDULE));

        // 현재는 작성자만 사용가능
        if (schedule.isDeleted()) {
            throw new CustomException(ErrorCode.ALREADY_DELETED);
        }
        if (!schedule.getMember().getId().equals(member.getId())) {
            throw new CustomException(ErrorCode.NO_AUTHORITY_TO_DELETE);
        }

        schedule.delete();

        return ScheduleDeleteResponse.of(scheduleId);
    }

    @Transactional(readOnly = true)
    public ScheduleDetailResponse getScheduleDetail(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_SCHEDULE));
        if(schedule.isDeleted()) {
            throw new CustomException(ErrorCode.ALREADY_DELETED);
        }

        List<CommentResponse> comments = schedule.getComments().stream()
                .map(comment -> CommentResponse.of(
                        comment.getId(), comment.getMember().getId(), comment.getSchedule().getId(),
                        comment.getMember().getNickname(), comment.getContent(),
                        comment.getCreatedAt(), comment.getUpdatedAt()
                ))
                .collect(Collectors.toList());

        return ScheduleDetailResponse.of(
                schedule.getId(), schedule.getMember().getId(),
                schedule.getTitle(), schedule.getMember().getNickname(), schedule.getContent(),
                schedule.getCreatedAt(), schedule.getUpdatedAt(), comments
        );
    }

    @Transactional(readOnly = true)
    public List<ScheduleElementResponse> getScheduleByUUID(UUID id) {
        Member owner = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER_UUID));

        return scheduleRepository.findByMember(owner)
                .stream()
                .filter(schedule -> !schedule.isDeleted())
                .map(this::makeScheduleElementResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ScheduleElementResponse> getScheduleByUUIDIncludingDeleted(UUID id) {
        Member owner = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER_UUID));

        return scheduleRepository.findByMember(owner)
                .stream()
                .map(this::makeScheduleElementResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ScheduleElementResponse> findAll() {
        return scheduleRepository.findAll()
                .stream()
                .filter(schedule -> !schedule.isDeleted())
                .map(this::makeScheduleElementResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ScheduleElementResponse> findAllIncludingDeleted() {
        return scheduleRepository.findAll()
                .stream()
                .map(this::makeScheduleElementResponse)
                .collect(Collectors.toList());
    }
}