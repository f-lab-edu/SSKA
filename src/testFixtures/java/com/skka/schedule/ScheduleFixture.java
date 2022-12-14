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
        2L
    );

    public static Schedule MOVING_SCHEDULE = Schedule.of(
        CUSTOMER,
        MOVING_STUDY_SEAT,
        LocalDateTime.now(),
        2L
    );
}
