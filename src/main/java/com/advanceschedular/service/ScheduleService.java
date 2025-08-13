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
@Transactional(readOnly = true)
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

    private Member getMember(UUID id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.SCH_INVALID_WRITER));
    }

    private Schedule getSchedule(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.SCH_INVALID_SCHEDULE));
    }

    @Transactional
    public ScheduleUploadResponse uploadSchedule(UUID memberId, ScheduleUploadRequest dto) {
        Member member = getMember(memberId);

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
        Member member = getMember(memberId);
        Schedule schedule = getSchedule(scheduleId);

        if (schedule.isDeleted()) {
            throw new CustomException(ErrorCode.SCH_ALREADY_DELETED);
        }
        if (!schedule.getMember().getId().equals(member.getId())) {
            throw new CustomException(ErrorCode.SCH_NO_AUTHORITY_TO_REVISE);
        }

        schedule.update(dto.getTitle(), dto.getContent());

        return ScheduleReviseResponse.of(schedule.getId(), schedule.getMember().getId());
    }

    @Transactional
    public ScheduleDeleteResponse deleteSchedule(Long scheduleId, UUID memberId) {
        Member member = getMember(memberId);
        Schedule schedule = getSchedule(scheduleId);

        if (schedule.isDeleted()) {
            throw new CustomException(ErrorCode.SCH_ALREADY_DELETED);
        }
        if (!schedule.getMember().getId().equals(member.getId())) {
            throw new CustomException(ErrorCode.SCH_NO_AUTHORITY_TO_DELETE);
        }

        schedule.delete();

        return ScheduleDeleteResponse.of(scheduleId);
    }

    public ScheduleDetailResponse getScheduleDetail(Long id) {
        Schedule schedule = getSchedule(id);

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

    public List<ScheduleElementResponse> getSchedulesByUUID(UUID id) {
        Member owner = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USR_INVALID_USER_UUID));

        return scheduleRepository.findByMember(owner)
                .stream()
                .filter(schedule -> !schedule.isDeleted())
                .map(this::makeScheduleElementResponse)
                .collect(Collectors.toList());
    }

    public List<ScheduleElementResponse> getSchedulesByUUIDIncludingDeleted(UUID id) {
        Member owner = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USR_INVALID_USER_UUID));

        return scheduleRepository.findByMember(owner)
                .stream()
                .map(this::makeScheduleElementResponse)
                .collect(Collectors.toList());
    }

    public List<ScheduleElementResponse> getAllSchedules() {
        return scheduleRepository.findAll()
                .stream()
                .filter(schedule -> !schedule.isDeleted())
                .map(this::makeScheduleElementResponse)
                .collect(Collectors.toList());
    }

    public List<ScheduleElementResponse> getAllSchedulesIncludingDeleted() {
        return scheduleRepository.findAll()
                .stream()
                .map(this::makeScheduleElementResponse)
                .collect(Collectors.toList());
    }
}