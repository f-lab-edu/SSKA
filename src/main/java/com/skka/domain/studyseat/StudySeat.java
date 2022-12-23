package com.skka.domain.studyseat;

import static com.skka.adaptor.common.exception.ErrorType.INVALID_STUDY_SEAT_OCCUPIED;
import static com.skka.adaptor.common.exception.ErrorType.INVALID_STUDY_SEAT_SEAT_NUMBER;
import static com.skka.adaptor.util.Util.requireStudySeat;

import com.skka.domain.schedule.Schedule;
import java.util.ArrayList;
import java.util.List;
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
    private String occupied;

    @OneToMany(mappedBy = "studySeat")
    private List<Schedule> schedules = new ArrayList<>();

    private StudySeat(long id, String seatNumber, String occupied) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.occupied = occupied;
    }

    public static StudySeat of(
        final long id,
        final String seatNumber,
        final String occupied
    ) {
        requireStudySeat(o -> seatNumber == null, seatNumber, INVALID_STUDY_SEAT_SEAT_NUMBER);
        requireStudySeat(o -> occupied == null, occupied, INVALID_STUDY_SEAT_OCCUPIED);

        return new StudySeat(id, seatNumber, occupied);
    }
}
