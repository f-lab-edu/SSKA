package com.skka.domain.studyseat.schedule;

import static com.skka.adapter.common.exception.ErrorType.INVALID_SCHEDULE_STATE;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ScheduleState {
    RESERVED("reserve"),
    CHECKED_OUT("check-out"),
    EXPIRED("expire");

    private final String state;

    public String getState() {
        return state;
    }

    public static ScheduleState from(final String type) {
        return Arrays.stream(values())
            .filter(value -> value.getState().equalsIgnoreCase(type))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(String.valueOf(INVALID_SCHEDULE_STATE)));
    }
}
