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
    INVALID_STUDY_SEAT_OCCUPIED(3004, "좌석 차지 여부는 NULL일 수 없습니다."),

    INVALID_SCHEDULE_END_TIME(4001, "끝나는 시간은 시작 시간 보다 이전일 수 없습니다."),
    INVALID_SCHEDULE_CUSTOMER(4002, "올바르지 않은 고객 입니다."),
    INVALID_SCHEDULE_STUDY_SEAT(4003, "올바르지 않은 좌석 입니다."),
    INVALID_SCHEDULE_ALREADY_RESERVED(4004, "이미 예약된 좌석 입니다."),
    INVALID_SCHEDULE_BEFORE_A_HOUR(4005, "이용 시간은 최소 1시간 이상 입니다."),
    INVALID_SCHEDULE_RESERVATION_ALREADY_OCCUPIED(4006, "다른 스케쥴과 겹칩니다."),
    INVALID_MY_SCHEDULE(4008, "자신의 예약 정보가 아닙니다.");

    private final int code;
    private final String message;
}
