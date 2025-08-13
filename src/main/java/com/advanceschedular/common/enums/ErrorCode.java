package com.advanceschedular.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_INPUT("VAL-001", "입력값이 유효하지 않습니다"),
    DUPLICATE_USERNAME("USR-001", "해당 ID로 이미 가입이 되어있습니다"),
    DUPLICATE_EMAIL("USR-002", "해당 닉네임으로 이미 가입이 되어있습니다"),
    WRONG_LOGIN_INFO("USR-003", "유효하지 않은 아이디 또는 비밀번호입니다"),
    INVALID_USER_UUID("USR-004", "유효하지 않은 유저 UUID 입니다."),
    INVALID_WRITER("SCH-001", "작성자의 정보가 유효하지 않습니다"),
    INVALID_SCHEDULE("SCH-002", "유효하지 않은 스케쥴입니다"),
    NO_AUTHORITY_TO_REVISE("SCH-003", "스케쥴을 수정할 권한이 없습니다"),
    NO_AUTHORITY_TO_DELETE("SCH-004", "스케쥴을 수정할 권한이 없습니다"),
    ALREADY_DELETED("SCH-005", "이미 삭제된 스케줄입니다");

    private final String code;
    private final String message;
}
