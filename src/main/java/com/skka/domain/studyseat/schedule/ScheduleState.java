package com.skka.domain.studyseat.schedule;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ScheduleState {
    RESERVED,
    CANCELED,
    EXPIRED
}
