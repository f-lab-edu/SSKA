package com.skka.application.studyseat.response;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CommandMoveSeatOrChangeTimeResponse {
    private String message;
    private Long movedSeatId;
    private LocalDateTime changingStartedTime;
    private LocalDateTime changingEndTime;

    public CommandMoveSeatOrChangeTimeResponse(
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

    public static CommandMoveSeatOrChangeTimeResponse of(
        final String message,
        final Long movedSeatId
    ) {
        return new CommandMoveSeatOrChangeTimeResponse(
            message, movedSeatId,
            null, null
        );
    }

    public static CommandMoveSeatOrChangeTimeResponse of(
        final String message,
        final LocalDateTime changingStartedTime,
        final LocalDateTime changingEndTime
    ) {
        return new CommandMoveSeatOrChangeTimeResponse(
            message, null,
            changingStartedTime, changingEndTime
        );
    }
}
