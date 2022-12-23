package com.skka.domain.schedule;

import static com.skka.adaptor.util.Util.requireSchedule;

import com.skka.adaptor.common.exception.ErrorType;
import com.skka.domain.customer.Customer;
import com.skka.domain.studyseat.StudySeat;
import java.time.LocalDateTime;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "schedule")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"studySeat", "customer"})
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

    @Embedded
    private StartEndTime startEndTime;

    private Schedule(
        final Customer customer,
        final StudySeat studySeat,
        final LocalDateTime startTime,
        final long addTime
    ) {
        this.customer = customer;
        this.studySeat = studySeat;
        this.startEndTime = StartEndTime.of(startTime, startTime.plusHours(addTime));
    }

    public static Schedule of(
        final Customer customer,
        final StudySeat studySeat,
        final LocalDateTime startTime,
        final long addTime
    ) {
        requireSchedule(o -> customer == null, customer, ErrorType.INVALID_SCHEDULE_CUSTOMER);
        requireSchedule(o -> studySeat == null, studySeat, ErrorType.INVALID_SCHEDULE_STUDY_SEAT);

        return new Schedule(customer, studySeat, startTime, addTime);
    }

    public void updateStudySeat(Customer customer, StudySeat studySeat) {
        this.customer = customer;
        this.studySeat = studySeat;
    }
}
