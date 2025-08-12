package com.advanceschedular.service;

import com.advanceschedular.dto.MemberSignUpRequest;
import com.advanceschedular.model.Member;
import com.advanceschedular.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public void signUpMember(MemberSignUpRequest memberSignUpRequest) {


        Member member = Member.builder().
                username(memberSignUpRequest.getUsername()).
                password(memberSignUpRequest.getPassword()).
                nickname(memberSignUpRequest.getNickname()).
                email(memberSignUpRequest.getEmail()).
                build();
    }
}
