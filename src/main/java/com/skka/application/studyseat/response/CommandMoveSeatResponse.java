package com.skka.application.studyseat.response;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CommandMoveSeatResponse {
    private String message;
    private Long movedSeatId;
    private LocalDateTime changingStartedTime;
    private LocalDateTime changingEndTime;

    public CommandMoveSeatResponse(
        final String message,
        final Long movedSeatId,
        final LocalDateTime changingStartedTime,
        final LocalDateTime changingEndTime
    ) {
        this.message = message;
        this.movedSeatId = movedSeatId;
        this.changingStartedTime = changingStartedTime;
        this.changingEndTime = changingEndTime;
    }

    public static CommandMoveSeatResponse of(
        final String message,
        final Long movedSeatId
    ) {
        return new CommandMoveSeatResponse(
            message, movedSeatId,
            null, null
        );
    }

    public static CommandMoveSeatResponse of(
        final String message,
        final LocalDateTime changingStartedTime,
        final LocalDateTime changingEndTime
    ) {
        return new CommandMoveSeatResponse(
            message, null,
            changingStartedTime, changingEndTime
        );
    }
}
