package com.skka.schedule;

import static com.skka.customer.CustomerFixture.CUSTOMER;
import static com.skka.studyseat.StudySeatFixture.MOVING_STUDY_SEAT;
import static com.skka.studyseat.StudySeatFixture.STUDY_SEAT;

import com.skka.domain.schedule.Schedule;
import java.time.LocalDateTime;

public class ScheduleFixture {

    public static Schedule SCHEDULE = Schedule.of(
        CUSTOMER,
        STUDY_SEAT,
        LocalDateTime.now(),
        LocalDateTime.now().plusHours(2L)
    );

    public static Schedule MOVING_SCHEDULE = Schedule.of(
        CUSTOMER,
        MOVING_STUDY_SEAT,
        LocalDateTime.now(),
        LocalDateTime.now().plusHours(2L)
    );

    public static Schedule SCHEDUEL_ERROR = Schedule.of(
        CUSTOMER,
        STUDY_SEAT,
        LocalDateTime.of(2021, 1, 10, 10, 10, 0),
        LocalDateTime.of(2021, 1, 10, 11, 10, 0)
    );
}
