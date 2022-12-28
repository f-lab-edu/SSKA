package com.skka.domain.schedule;

import static com.skka.adaptor.common.exception.ErrorType.INVALID_SCHEDULE_BEFORE_A_HOUR;
import static com.skka.adaptor.common.exception.ErrorType.INVALID_SCHEDULE_CUSTOMER;
import static com.skka.adaptor.common.exception.ErrorType.INVALID_SCHEDULE_STUDY_SEAT;
import static com.skka.adaptor.util.Util.require;

import com.skka.domain.customer.Customer;
import com.skka.domain.studyseat.StudySeat;
import java.time.Duration;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Entity(name = "schedule")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "study_seat_id")
    private StudySeat studySeat;

    @Column(name = "started_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startedTime;

    @Column(name = "end_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endTime;

    private Schedule(
        final Customer customer,
        final StudySeat studySeat,
        final LocalDateTime startTime,
        final long addHour
    ) {
        this.customer = customer;
        this.studySeat = studySeat;
        this.startedTime = startTime;
        this.endTime = startTime.plusHours(addHour);
    }

    public static Schedule of(
        final Customer customer,
        final StudySeat studySeat,
        final LocalDateTime startTime,
        final long addHour
    ) {
        require(o -> customer == null, customer, INVALID_SCHEDULE_CUSTOMER);
        require(o -> studySeat == null, studySeat, INVALID_SCHEDULE_STUDY_SEAT);
        require(
            o -> checkTimeDifference(startTime, startTime.plusHours(addHour)) < 1,
            checkTimeDifference(startTime, startTime.plusHours(addHour)),
            INVALID_SCHEDULE_BEFORE_A_HOUR)
        ;

        return new Schedule(customer, studySeat, startTime, addHour);
    }

    private static long checkTimeDifference(
        final LocalDateTime startedTime,
        final LocalDateTime endTime
    ) {
        Duration diff = Duration.between(startedTime, endTime);
        return diff.toHours();
    }
}
