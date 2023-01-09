package com.skka.application.customer.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommandAddStudyTimeResponse {
    private final String message;
    private final LocalDateTime changedEndTime;
}
