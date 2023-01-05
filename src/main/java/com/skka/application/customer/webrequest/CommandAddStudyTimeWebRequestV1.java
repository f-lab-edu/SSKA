package com.skka.application.customer.webrequest;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@RequiredArgsConstructor
@ToString
public class CommandAddStudyTimeWebRequestV1 {
    private final long customerId;
    private final long studySeatId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime startedTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime endTime;

    private final long plusHour;
}
