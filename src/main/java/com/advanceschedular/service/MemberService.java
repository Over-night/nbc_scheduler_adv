package com.advanceschedular.service;

import com.advanceschedular.common.enums.ErrorCode;
import com.advanceschedular.common.exception.CustomException;
import com.advanceschedular.common.util.PasswordUtil;
import com.advanceschedular.dto.MemberSignInRequest;
import com.advanceschedular.dto.MemberSignUpRequest;
import com.advanceschedular.model.Member;
import com.advanceschedular.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public UUID signUpMember(MemberSignUpRequest memberSignUpRequest) {
        if (memberRepository.existsByUsername(memberSignUpRequest.getUsername()))
            throw new CustomException(ErrorCode.DUPLICATE_USERNAME);
        if (memberRepository.existsByEmail(memberSignUpRequest.getEmail()))
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);

        String encodedPassword = PasswordUtil.encode(memberSignUpRequest.getPassword());

        Member member = Member.builder().
                username(memberSignUpRequest.getUsername()).
                password(encodedPassword).
                nickname(memberSignUpRequest.getNickname()).
                email(memberSignUpRequest.getEmail()).
                build();

        memberRepository.save(member);

        return member.getId();
    }

    @Transactional
    public Member signInMember(MemberSignInRequest memberSignInRequest) {
        Optional<Member> member = memberRepository.findByUsername(memberSignInRequest.getUsername());
        if (member.isEmpty() ||
                !PasswordUtil.matches(memberSignInRequest.getPassword(), member.get().getPassword())) {
            throw new CustomException(ErrorCode.WRONG_LOGIN_INFO);
        }

        return member.get();
    }
}
