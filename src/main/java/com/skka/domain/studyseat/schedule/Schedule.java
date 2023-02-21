package com.skka.domain.studyseat.schedule;

import static com.skka.adapter.common.exception.ErrorType.INVALID_SCHEDULE_BEFORE_A_HOUR;
import static com.skka.adapter.common.exception.ErrorType.INVALID_SCHEDULE_CUSTOMER;
import static com.skka.adapter.common.exception.ErrorType.INVALID_SCHEDULE_STUDY_SEAT;
import static com.skka.adapter.util.Util.checkTimeDifference;
import static com.skka.adapter.util.Util.require;

import com.skka.domain.customer.Customer;
import com.skka.domain.studyseat.StudySeat;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString(exclude = {"studySeat", "customer"})
@RequiredArgsConstructor
public class Schedule {

    private long id;
    private Customer customer;
    private StudySeat studySeat;
    private LocalDateTime startedTime;
    private LocalDateTime endTime;
    private ScheduleState state;

    private Schedule(
        final Customer customer,
        final StudySeat studySeat,
        final LocalDateTime startTime,
        final LocalDateTime endTime
    ) {
        this.customer = customer;
        this.studySeat = studySeat;
        this.startedTime = startTime;
        this.endTime = endTime;
        this.state = ScheduleState.RESERVED;
    }

    private Schedule(
        final long id,
        final Customer customer,
        final StudySeat studySeat,
        final LocalDateTime startTime,
        final LocalDateTime endTime
    ) {
        this.id = id;
        this.customer = customer;
        this.studySeat = studySeat;
        this.startedTime = startTime;
        this.endTime = endTime;
        this.state = ScheduleState.RESERVED;
    }

    public static Schedule of(
        final Customer customer,
        final StudySeat studySeat,
        final LocalDateTime startTime,
        final LocalDateTime endTime
    ) {
        require(o -> customer == null, customer, INVALID_SCHEDULE_CUSTOMER);
        require(o -> studySeat == null, studySeat, INVALID_SCHEDULE_STUDY_SEAT);
        require(
            o -> checkTimeDifference(startTime, endTime) < 1,
            checkTimeDifference(startTime, endTime),
            INVALID_SCHEDULE_BEFORE_A_HOUR)
        ;

        return new Schedule(customer, studySeat, startTime, endTime);
    }

    public static Schedule of(
        final long id,
        final Customer customer,
        final StudySeat studySeat,
        final LocalDateTime startTime,
        final LocalDateTime endTime
    ) {
        require(o -> customer == null, customer, INVALID_SCHEDULE_CUSTOMER);
        require(o -> studySeat == null, studySeat, INVALID_SCHEDULE_STUDY_SEAT);
        require(
            o -> checkTimeDifference(startTime, endTime) < 1,
            checkTimeDifference(startTime, endTime),
            INVALID_SCHEDULE_BEFORE_A_HOUR)
        ;

        return new Schedule(id, customer, studySeat, startTime, endTime);
    }

    public void checkout(final String scheduleState) {
        this.state = ScheduleState.from(scheduleState);
    }
}
