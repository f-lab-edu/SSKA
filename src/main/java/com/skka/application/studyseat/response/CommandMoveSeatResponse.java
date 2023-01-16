package com.skka.application.studyseat.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommandMoveSeatResponse {
    private final String message;
    private final long extractedScheduleId;
    private final long studySeatIdExtractedSchedule;
}
