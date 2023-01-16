package com.skka.application.studyseat.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class ChangeStudyTimeRequest {
    private final long customerId;
    private final LocalDateTime changingStartedTime;
    private final LocalDateTime changingEndTime;
}
