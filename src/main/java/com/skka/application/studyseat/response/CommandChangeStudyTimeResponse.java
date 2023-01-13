package com.skka.application.studyseat.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommandChangeStudyTimeResponse {
    private final String message;
    private final LocalDateTime changedEndTime;
}
