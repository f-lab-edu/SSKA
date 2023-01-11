package com.skka.domain.studyseat;

import static com.skka.adaptor.common.exception.ErrorType.INVALID_SCHEDULE_RESERVATION_ALREADY_OCCUPIED;
import static com.skka.adaptor.common.exception.ErrorType.INVALID_STUDY_SEAT_SEAT_NUMBER;
import static com.skka.adaptor.util.Util.check;
import static com.skka.adaptor.util.Util.require;

import com.skka.domain.schedule.Schedule;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "study_seat")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
public class StudySeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String seatNumber;
    private boolean occupied;

    @OneToMany(mappedBy = "studySeat")
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
        final LocalDateTime a,
        final LocalDateTime b,
        final LocalDateTime c,
        final LocalDateTime d
    ) {
        return isAfterOrEquals(a, b) && isAfterOrEquals(c, d);
    }

    private boolean isAfterOrEquals(
        final LocalDateTime a,
        final LocalDateTime b
    ) {
        return a.compareTo(b) > 0;
    }


    public void reserve(Schedule schedule) {
        schedules.add(schedule);
    }

}
