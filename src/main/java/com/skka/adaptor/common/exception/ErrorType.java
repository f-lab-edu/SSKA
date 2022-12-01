package com.skka.adaptor.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {

    INVALID_CUSTOMER_NAME(2001, "올바르지 않은 이름입니다."),
    INVALID_CUSTOMER_EMAIL(2002, "올바르지 않은 이메일입니다."),
    INVALID_CUSTOMER_TEL(2003, "올바르지 않은 전화번호입니다."),

    INVALID_STUDY_SEAT_STARTED_TIME(3001, "시작 시간은 현재보다 이전일 수 없습니다."),
    INVALID_STUDY_SEAT_END_TIME(3002, "끝나는 시간은 시작 시간 보다 이전일 수 없습니다."),
    INVALID_STUDY_SEAT_SEAT_NUMBER(3003, "올바르지 않은 좌석 번호 입니다."),
    INVALID_STUDY_SEAT_OCCUPIED(3004, "좌석 차지 여부는 NULL일 수 없습니다.");

    private final int code;
    private final String message;
}
