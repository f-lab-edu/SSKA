package com.skka.adapter.outbound.jpa.studyseat.schedule;

import static com.skka.adapter.common.exception.ErrorType.INVALID_SCHEDULE_BEFORE_A_HOUR;
import static com.skka.adapter.common.exception.ErrorType.INVALID_SCHEDULE_CUSTOMER;
import static com.skka.adapter.common.exception.ErrorType.INVALID_SCHEDULE_STUDY_SEAT;
import static com.skka.adapter.util.Util.checkTimeDifference;
import static com.skka.adapter.util.Util.require;

import com.skka.adapter.outbound.jpa.customer.CustomerEntity;
import com.skka.adapter.outbound.jpa.studyseat.StudySeatEntity;
import com.skka.domain.studyseat.schedule.ScheduleState;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

@Entity(name = "schedule")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(exclude = {"studySeat", "customer"})
@Where(clause = "state = 'RESERVED' AND started_time >= NOW()")
public class ScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private CustomerEntity customer;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "study_seat_id", referencedColumnName = "id")
    private StudySeatEntity studySeat;

    @Column(name = "started_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startedTime;

    @Column(name = "end_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endTime;

    @Enumerated(value = EnumType.STRING)
    private ScheduleState state;

    private ScheduleEntity(
        final long id,
        final CustomerEntity customerEntity,
        final StudySeatEntity studySeatEntity,
        final LocalDateTime startTime,
        final LocalDateTime endTime,
        final ScheduleState state
    ) {
        this.id = id;
        this.customer = customerEntity;
        this.studySeat = studySeatEntity;
        this.startedTime = startTime;
        this.endTime = endTime;
        this.state = state;
    }

    public static ScheduleEntity of(
        final long id,
        final CustomerEntity customerEntity,
        final StudySeatEntity studySeatEntity,
        final LocalDateTime startTime,
        final LocalDateTime endTime,
        final ScheduleState state
    ) {
        require(o -> customerEntity == null, customerEntity, INVALID_SCHEDULE_CUSTOMER);
        require(o -> studySeatEntity == null, studySeatEntity, INVALID_SCHEDULE_STUDY_SEAT);
        require(
            o -> checkTimeDifference(startTime, endTime) < 1,
            checkTimeDifference(startTime, endTime),
            INVALID_SCHEDULE_BEFORE_A_HOUR)
        ;

        return new ScheduleEntity(id, customerEntity, studySeatEntity, startTime, endTime, state);
    }
}
