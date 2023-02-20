package com.skka.adaptor.outbound.jpa.studyseat;

import static com.skka.adaptor.common.exception.ErrorType.INVALID_STUDY_SEAT_SEAT_NUMBER;
import static com.skka.adaptor.util.Util.require;

import com.skka.adaptor.outbound.jpa.studyseat.schedule.ScheduleEntity;
import com.skka.domain.studyseat.StudySeat;
import com.skka.domain.studyseat.schedule.Schedule;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
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
public class StudySeatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String seatNumber;
    private boolean occupied;

    @OneToMany(mappedBy = "studySeat", cascade = {CascadeType.ALL, CascadeType.MERGE}, orphanRemoval = true)
    private List<ScheduleEntity> schedules = new ArrayList<>();

    private StudySeatEntity(long id, String seatNumber, boolean occupied) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.occupied = occupied;
    }

    public static StudySeatEntity of(
        final long id,
        final String seatNumber,
        final boolean occupied
    ) {
        require(o -> seatNumber == null, seatNumber, INVALID_STUDY_SEAT_SEAT_NUMBER);

        return new StudySeatEntity(id, seatNumber, occupied);
    }

    public static StudySeatEntity of(
        final long id,
        final String seatNumber,
        final boolean occupied,
        final List<Schedule> schedule
    ) {
        require(o -> seatNumber == null, seatNumber, INVALID_STUDY_SEAT_SEAT_NUMBER);

        System.out.println("@@@ = " + schedule);

        return new StudySeatEntity(id, seatNumber, occupied);
    }

    public StudySeat toStudySeat() {
        return StudySeat.of(id, seatNumber, occupied);
    }

    public void setSchedules(final List<Schedule> schedule) {
        System.out.println("111 = " + schedule.get(0).getCustomer());
        schedule.forEach(ss -> schedules.add(ScheduleEntity.of(
                ss.getCustomer().toCustomerEntity(),
                ss.getStudySeat().toStudySeatEntity1(),
                ss.getStartedTime(),
                ss.getEndTime()
            )));
    }
}
