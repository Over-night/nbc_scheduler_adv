package com.advanceschedular.service;

import com.advanceschedular.common.enums.ErrorCode;
import com.advanceschedular.common.exception.CustomException;
import com.advanceschedular.common.util.PasswordUtil;
import com.advanceschedular.dto.member.MemberSignInRequest;
import com.advanceschedular.dto.member.MemberSignUpRequest;
import com.advanceschedular.model.Member;
import com.advanceschedular.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final MemberRepository memberRepository;

    public UUID signUpMember(MemberSignUpRequest memberSignUpRequest) {
        if (memberRepository.existsByUsername(memberSignUpRequest.getUsername()))
            throw new CustomException(ErrorCode.USR_DUPLICATE_ID);
        if (memberRepository.existsByEmail(memberSignUpRequest.getEmail()))
            throw new CustomException(ErrorCode.USR_DUPLICATE_EMAIL);

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

    public Member signInMember(MemberSignInRequest memberSignInRequest) {
        Optional<Member> member = memberRepository.findByUsername(memberSignInRequest.getUsername());
        if (member.isEmpty() ||
                !PasswordUtil.matches(memberSignInRequest.getPassword(), member.get().getPassword())) {
            throw new CustomException(ErrorCode.USR_WRONG_LOGIN_INFO);
        }

        return member.get();
    }
}
