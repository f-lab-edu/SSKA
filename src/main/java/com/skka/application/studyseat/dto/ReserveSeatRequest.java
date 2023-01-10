package com.skka.application.studyseat.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReserveSeatRequest {
    private final long customerId;
    private final LocalDateTime startedTime;
    private final LocalDateTime endTime;
}
