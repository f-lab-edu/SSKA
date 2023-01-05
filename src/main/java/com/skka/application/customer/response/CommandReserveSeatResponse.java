package com.skka.application.customer.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommandReserveSeatResponse {
    private final String message;
    private final long reservedSeatId;
}
