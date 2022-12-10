package com.skka.domain.studyseat;

import static com.skka.adaptor.common.exception.ErrorType.INVALID_STUDY_SEAT_END_TIME;

import com.skka.adaptor.common.exception.ErrorType;
import com.skka.domain.studyseat.error.InvalidStudySeatException;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class StartEndTime {

    @Column(name = "started_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startedTime;

    @Column(name = "end_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endTime;

    public StartEndTime(LocalDateTime startedTime, LocalDateTime endTime) {
        this.startedTime = startedTime;
        this.endTime = endTime;
    }

    public static StartEndTime of(LocalDateTime startedTime, LocalDateTime endTime) {

        require(!endTime.isBefore(startedTime), INVALID_STUDY_SEAT_END_TIME);

        return new StartEndTime(startedTime, endTime);
    }

    private static void require(final boolean condition, final ErrorType msg) {
        if (!condition) {
            throw new InvalidStudySeatException(msg);
        }
    }
}
