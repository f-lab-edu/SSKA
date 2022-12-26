package com.skka.domain.schedule;

import static com.skka.adaptor.common.exception.ErrorType.INVALID_SCHEDULE_BEFORE_A_HOUR;
import static com.skka.adaptor.util.Util.require;

import java.time.Duration;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    private StartEndTime(
        final LocalDateTime startedTime,
        final LocalDateTime endTime
    ) {
        this.startedTime = startedTime;
        this.endTime = endTime;
    }

    public static StartEndTime of(
        final LocalDateTime startedTime,
        final LocalDateTime endTime
    ) {
        require(
            o -> checkTimeDifference(startedTime, endTime) < 1,
            checkTimeDifference(startedTime, endTime),
            INVALID_SCHEDULE_BEFORE_A_HOUR)
        ;

        return new StartEndTime(startedTime, endTime);
    }

    private static long checkTimeDifference(
        final LocalDateTime startedTime,
        final LocalDateTime endTime
    ) {
        Duration diff = Duration.between(startedTime, endTime);
        return diff.toHours();
    }
}
