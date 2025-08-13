package com.advanceschedular.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_INPUT("VAL-001", "입력값이 유효하지 않습니다"),
    DUPLICATE_USERNAME("USR-001", "해당 ID로 이미 가입이 되어있습니다"),
    DUPLICATE_EMAIL("USR-002", "해당 닉네임으로 이미 가입이 되어있습니다"),
    WRONG_LOGIN_INFO("USR-003", "유효하지 않은 아이디 또는 비밀번호입니다.");

    private final String code;
    private final String message;
}
