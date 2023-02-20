package com.skka.domain.studyseat;

import static com.skka.adapter.common.exception.ErrorType.INVALID_SCHEDULE_BEFORE_A_HOUR;
import static com.skka.adapter.common.exception.ErrorType.INVALID_STUDY_SEAT_SEAT_NUMBER;
import static com.skka.adapter.common.exception.ErrorType.SCHEDULE_NOT_EXISTED;
import static com.skka.adapter.util.Util.check;
import static com.skka.adapter.util.Util.checkTimeDifference;
import static com.skka.adapter.util.Util.require;

import com.skka.adapter.outbound.jpa.studyseat.StudySeatEntity;
import com.skka.domain.customer.Customer;
import com.skka.domain.studyseat.schedule.Schedule;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class StudySeat {

    private long id;
    private String seatNumber;
    private boolean occupied;
    private List<Schedule> schedules = new ArrayList<>();

    private StudySeat(long id, String seatNumber, boolean occupied) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.occupied = occupied;
    }

    public static StudySeat of(
        final long id,
        final String seatNumber,
        final boolean occupied
    ) {
        require(o -> seatNumber == null, seatNumber, INVALID_STUDY_SEAT_SEAT_NUMBER);

        return new StudySeat(id, seatNumber, occupied);
    }

    public boolean isReservable(
        final LocalDateTime startedTime,
        final LocalDateTime endTime
    ) {
        Optional<Schedule> overlappedSchedules = this.schedules.stream()
            .filter(s -> checkAllOverlappedSchedules(
                s.getStartedTime(),
                s.getEndTime(),
                startedTime,
                endTime)
            ).findFirst();
        return overlappedSchedules.isPresent();
    }

    private boolean checkAllOverlappedSchedules(
        final LocalDateTime dbStartedTime,
        final LocalDateTime dbEndTime,
        final LocalDateTime startedTime,
        final LocalDateTime endTime
    ) {
        return (
            (isBetween(dbStartedTime, startedTime, endTime, dbStartedTime))
            || (isBetween(dbEndTime, startedTime, endTime, dbEndTime))
            || (isBetween(startedTime, dbStartedTime, dbEndTime, endTime))
            || startedTime.isEqual(dbStartedTime) || endTime.isEqual(dbEndTime)
        );
    }

    private boolean isBetween(
        final LocalDateTime time1,
        final LocalDateTime time2,
        final LocalDateTime time3,
        final LocalDateTime time4
    ) {
        return isAfterOrEquals(time1, time2) && isAfterOrEquals(time3, time4);
    }

    private boolean isAfterOrEquals(
        final LocalDateTime a,
        final LocalDateTime b
    ) {
        return a.compareTo(b) > 0;
    }


    public void reserve(
        final Customer customer,
        final StudySeat studySeat,
        final LocalDateTime startedTime,
        final LocalDateTime endTime
    ) {
        Schedule schedule = Schedule.of(
            customer,
            studySeat,
            startedTime,
            endTime
        );
        schedules.add(schedule);
    }


    public Schedule extractScheduleWith(final long scheduleId) {
        Optional<Schedule> schedule = this.schedules.stream()
            .filter(s -> s.getId() == scheduleId).findFirst();

        checkIfScheduleEmpty(schedule);
        schedules.remove(schedule.get());

        return schedule.get();
    }

    private void checkIfScheduleEmpty(final Optional<Schedule> schedule) {
        check(schedule.isEmpty(), SCHEDULE_NOT_EXISTED);
    }

    private Schedule findScheduleById(final long scheduleId) {
        Optional<Schedule> schedule = this.schedules.stream()
            .filter(s -> s.getId() == scheduleId)
            .findFirst();

        check(schedule.isEmpty(), SCHEDULE_NOT_EXISTED);

        return schedule.get();
    }


    public void checkout(final long scheduleId, final String scheduleState) {
        Schedule schedule = findScheduleById(scheduleId);
        schedule.checkout(scheduleState);
    }

    public void checkBeneathOfAHour(
        final LocalDateTime changingStartedTime,
        final LocalDateTime changingEndTime
    ) {
        require(o -> checkTimeDifference(
                changingStartedTime, changingEndTime) < 1,
            checkTimeDifference(changingStartedTime, changingEndTime),
            INVALID_SCHEDULE_BEFORE_A_HOUR)
        ;
    }

    public StudySeatEntity toStudySeatEntityWithScheduleEntity() {
        StudySeatEntity studySeatEntity = StudySeatEntity.of(id, seatNumber, occupied);
        studySeatEntity.setScheduleInEntity(schedules);
        return studySeatEntity;
    }

    public StudySeatEntity toStudySeatEntity() {
        return StudySeatEntity.of(id, seatNumber, occupied);
    }
}
