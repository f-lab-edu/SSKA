package com.skka.adaptor.inbound.api.studyseat.webrequest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommandCheckoutScheduleWebRequestV1 {

    private final String scheduleState;
}