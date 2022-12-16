package com.skka.studyseat;

import com.skka.domain.studyseat.StartEndTime;
import com.skka.domain.studyseat.StudySeat;
import java.time.LocalDateTime;

public class StudySeatFixture {

    private static final LocalDateTime time = LocalDateTime.now();

    public static StudySeat STUDY_SEAT = StudySeat.of(
        1L,
        "1",
        "Y",
        time, time.plusHours(3L)
    );

    public static StudySeat studySeatConstructor(LocalDateTime startedTime, LocalDateTime endTime) {

        StartEndTime.of(startedTime, endTime);

        return StudySeat.of(
            1L,
            "1",
            "Y",
            startedTime, endTime
        );
    }
}
