package com.advanceschedular.controller;

import com.advanceschedular.dto.member.MemberSignInRequest;
import com.advanceschedular.dto.member.MemberSignUpRequest;
import com.advanceschedular.dto.member.MemberSignUpResponse;
import com.advanceschedular.model.Member;
import com.advanceschedular.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<MemberSignUpResponse> signUp(@Valid @RequestBody MemberSignUpRequest memberSignUpRequest) {
        UUID id = authService.signUpMember(memberSignUpRequest);
        MemberSignUpResponse memberSignUpResponse =
                MemberSignUpResponse.created(id, memberSignUpRequest.getNickname()
        );
        return ResponseEntity
                .created(URI.create("/api/v1/members/" + id))
                .body(memberSignUpResponse);
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signIn(HttpServletRequest request,
                                         @Valid @RequestBody MemberSignInRequest memberSignInRequest) {
        Member member = authService.signInMember(memberSignInRequest);

        HttpSession session = request.getSession(); // 신규 세션 생성, JSESSIONID 쿠키 발급
        session.setAttribute("LOGIN_USER", member.getId());

        return ResponseEntity.ok("로그인 성공");
    }
}
