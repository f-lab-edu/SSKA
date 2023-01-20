package com.skka.application.studyseat.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CheckoutScheduleRequest {
    private final long customerId;
    private final LocalDateTime changingStartedTime;
    private final LocalDateTime changingEndTime;
}
