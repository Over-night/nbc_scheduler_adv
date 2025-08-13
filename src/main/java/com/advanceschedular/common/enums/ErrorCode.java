package com.advanceschedular.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    VAL_INVALID_INPUT("VAL-001", "입력값이 유효하지 않습니다"),
    USR_DUPLICATE_ID("USR-001", "해당 ID로 이미 가입이 되어있습니다"),
    USR_DUPLICATE_EMAIL("USR-002", "해당 닉네임으로 이미 가입이 되어있습니다"),
    USR_WRONG_LOGIN_INFO("USR-003", "유효하지 않은 아이디 또는 비밀번호입니다"),
    USR_INVALID_USER_UUID("USR-004", "유효하지 않은 유저 UUID 입니다"),
    USR_UNAUTHORIZED("USR-005", "인증이 필요합니다"),
    SCH_INVALID_WRITER("SCH-001", "작성자의 정보가 유효하지 않습니다"),
    SCH_INVALID_SCHEDULE("SCH-002", "유효하지 않은 스케쥴입니다"),
    SCH_NO_AUTHORITY_TO_REVISE("SCH-003", "스케쥴을 수정할 권한이 없습니다"),
    SCH_NO_AUTHORITY_TO_DELETE("SCH-004", "스케쥴을 삭제할 권한이 없습니다"),
    SCH_ALREADY_DELETED("SCH-005", "삭제된 스케줄입니다"),
    CMT_INVALID_WRITER("CMT-001", "작성자의 정보가 유효하지 않습니다"),
    CMT_INVALID_SCHEDULE("CMT-002", "유효하지 않은 스케쥴입니다"),
    CMT_INVALID_COMMENT("CMT-003", "유효하지 않은 댓글입니다"),
    CMT_DELETED_SCHEDULE("CMT-004", "삭제된 스케줄에는 접근할 수 없습니다"),
    CMT_ALREADY_DELETED("CMT-005", "삭제된 댓글입니다"),
    CMT_NO_AUTHORITY_TO_REVISE("CMT-006", "댓글을 수정할 권한이 없습니다"),
    CMT_NO_AUTHORITY_TO_DELETE("CMT-007", "댓글을 삭제할 권한이 없습니다"),
    CMT_CANNOT_FIND_USER("CMT-008", "해당 작성자의 댓글을 찾을 수 없습니다");

    private final String code;
    private final String message;
}
