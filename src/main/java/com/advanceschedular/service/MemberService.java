package com.advanceschedular.service;


import com.advanceschedular.common.enums.ErrorCode;
import com.advanceschedular.common.exception.CustomException;
import com.advanceschedular.dto.member.MemberResponse;
import com.advanceschedular.model.Member;
import com.advanceschedular.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    private MemberResponse makeMemberResponse(Member member) {
        return MemberResponse.of(member.getId(), member.getUsername(), member.getNickname(), member.getEmail(),
                member.getCreatedAt(), member.getUpdatedAt());
    };

    public MemberResponse getMemberByUUID(UUID id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USR_INVALID_USER_UUID));

        return makeMemberResponse(member);
    }

    public List<MemberResponse> getAllMembers() {
        return memberRepository.findAll()
                .stream()
                .filter(member -> !member.isDeleted())
                .map(this::makeMemberResponse)
                .collect(Collectors.toList());
    }
}
