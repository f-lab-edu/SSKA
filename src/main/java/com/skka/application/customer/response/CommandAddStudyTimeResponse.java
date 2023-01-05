package com.skka.application.customer.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommandAddStudyTimeResponse {
    private final String message;
    private final long addedStudySeatId;
    private final long addedHour;
}
