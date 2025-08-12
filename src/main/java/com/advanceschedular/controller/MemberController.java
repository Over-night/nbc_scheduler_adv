package com.advanceschedular.controller;

import com.advanceschedular.dto.MemberSignUpRequest;
import com.advanceschedular.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<String> signUp(@Valid @RequestBody MemberSignUpRequest memberSignUpRequest) {
        memberService.signUpMember(memberSignUpRequest);

        return ResponseEntity.ok("회원가입 성공");
    }
}
