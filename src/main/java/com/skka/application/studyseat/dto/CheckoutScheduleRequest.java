package com.skka.application.studyseat.dto;

import com.skka.domain.studyseat.schedule.ScheduleState;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CheckoutScheduleRequest {
    private final long customerId;
    private final String scheduleState;
}
