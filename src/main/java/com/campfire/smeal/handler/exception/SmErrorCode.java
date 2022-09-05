package com.campfire.smeal.handler.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SmErrorCode {
    NO_USER("사용자가 없습니다."),
    DUPLICATED_USER_ID("중복된 ID 입니다."),
    INCONSISTENCY_PASSWORD("비밀번호가 일치하지 않습니다."),
    INTERNAL_SERVER_ERROR("서버에 오류가 발생했습니다."),
    INVALID_REQUEST("잘못된 요청입니다."),

    LOGIN_ERROR("ID가 없거나 잘 못 입력하셨습니다."),

    NO_BOARD("존재하지 않는 게시판입니다."),

    NO_INPUT_VALUE("값을 입력하지 않았습니다.")

    ;

    private final String message;
}
