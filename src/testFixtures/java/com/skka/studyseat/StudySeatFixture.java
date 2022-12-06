package com.skka.studyseat;

import static com.skka.customer.CustomerFixture.CUSTOMER;

import com.skka.domain.studyseat.StartEndTime;
import com.skka.domain.studyseat.StudySeat;
import java.time.LocalDateTime;

public class StudySeatFixture {

    private static final LocalDateTime time = LocalDateTime.now();

    public static StudySeat STUDY_SEAT = new StudySeat(
        1L,
        "1",
        "Y",
        time, time.plusHours(3L),
        CUSTOMER
    );

    public static StudySeat studySeatConstructor(LocalDateTime startedTime, LocalDateTime endTime) {
        StartEndTime a = StartEndTime.of(startedTime, endTime);

        return new StudySeat(
            1L,
            "1",
            "Y",
            startedTime, endTime,
            CUSTOMER
        );
    }
}
