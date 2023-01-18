package com.skka.adaptor.controller.studyseat.webrequest;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@RequiredArgsConstructor
@ToString
public class CommandReserveSeatWebRequestV1 {
    private final long customerId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private final LocalDateTime startedTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private final LocalDateTime endTime;
}
