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
import javax.persistence.FetchType;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
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
        final LocalDateTime endTime
    ) {
        this.customer = customer;
        this.studySeat = studySeat;
        this.startedTime = startTime;
        this.endTime = endTime;
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

    private static long checkTimeDifference(
        final LocalDateTime startedTime,
        final LocalDateTime endTime
    ) {
        Duration diff = Duration.between(startedTime, endTime);
        return diff.toHours();
    }

    public void updateStudySeat(Customer customer, StudySeat studySeat) {
        this.customer = customer;
        this.studySeat = studySeat;
    }
}
