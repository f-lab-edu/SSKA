package com.skka.adaptor.controller.studyseat.webrequest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommandCheckoutScheduleWebRequestV1 {

    private final long customerId;
    private final String scheduleState;
}
