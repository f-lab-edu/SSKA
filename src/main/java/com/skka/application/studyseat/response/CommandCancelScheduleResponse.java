package com.skka.application.studyseat.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommandCancelScheduleResponse {
    private final String message;
    private final long canceledScheduleId;
}
