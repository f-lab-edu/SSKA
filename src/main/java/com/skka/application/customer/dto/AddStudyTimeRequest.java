package com.skka.application.customer.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AddStudyTimeRequest {
    private final long customerId;
    private final long studySeatId;
    private final LocalDateTime startedTime;
    private final LocalDateTime endTime;
    private final long plusHour;
}
