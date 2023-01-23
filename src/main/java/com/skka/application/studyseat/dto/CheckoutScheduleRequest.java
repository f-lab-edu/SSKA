package com.skka.application.studyseat.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CheckoutScheduleRequest {
    private final String scheduleState;
}
