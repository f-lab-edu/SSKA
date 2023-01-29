package com.skka.schedule;

import static com.skka.customer.CustomerFixture.CUSTOMER;
import static com.skka.studyseat.StudySeatFixture.MOVING_STUDY_SEAT;
import static com.skka.studyseat.StudySeatFixture.STUDY_SEAT;

import com.skka.domain.studyseat.schedule.Schedule;
import java.time.LocalDateTime;

public class ScheduleFixture {

    public static Schedule SCHEDULE = Schedule.of(
        CUSTOMER,
        STUDY_SEAT,
        LocalDateTime.of(2023, 1, 10, 12, 10),
        LocalDateTime.of(2023, 1, 10, 15, 10)
    );

    public static Schedule SCHEDULE_2 = Schedule.of(
        CUSTOMER,
        MOVING_STUDY_SEAT,
        LocalDateTime.of(2023, 1, 10, 13, 10),
        LocalDateTime.of(2023, 1, 10, 18, 10)
    );

    public static Schedule SCHEDULE_3 = Schedule.of(
        CUSTOMER,
        MOVING_STUDY_SEAT,
        LocalDateTime.of(2023, 1, 10, 13, 10),
        LocalDateTime.of(2023, 1, 10, 18, 10)
    );
}
