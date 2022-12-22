package com.skka.domain.studyseat;

import static com.skka.adaptor.common.exception.ErrorType.INVALID_STUDY_SEAT_OCCUPIED;
import static com.skka.adaptor.common.exception.ErrorType.INVALID_STUDY_SEAT_SEAT_NUMBER;

import com.skka.adaptor.common.exception.ErrorType;
import com.skka.domain.studyseat.error.InvalidStudySeatException;
import java.util.function.Predicate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
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

    public static StudySeat of(
        final long id,
        final String seatNumber,
        final String occupied
    ) {
        require(o -> seatNumber == null, seatNumber, INVALID_STUDY_SEAT_SEAT_NUMBER);
        require(o -> occupied == null, occupied, INVALID_STUDY_SEAT_OCCUPIED);

        return new StudySeat(id, seatNumber, occupied);
    }

    private static <T> void require(final Predicate<T> predicate, final T target, final ErrorType msg) {
        if (predicate.test(target)) {
            throw new InvalidStudySeatException(msg);
        }
    }
}
