package com.skka.adapter.outbound.jpa.studyseat;

import static com.skka.adapter.common.exception.ErrorType.INVALID_STUDY_SEAT_SEAT_NUMBER;
import static com.skka.adapter.util.EntityConverter.toCustomerEntity;
import static com.skka.adapter.util.EntityConverter.toStudySeatEntity;
import static com.skka.adapter.util.Util.require;

import com.skka.adapter.outbound.jpa.customer.CustomerEntity;
import com.skka.adapter.outbound.jpa.studyseat.schedule.ScheduleEntity;
import com.skka.domain.customer.Customer;
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

    @OneToMany(mappedBy = "studySeat", cascade = CascadeType.ALL, orphanRemoval = true)
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

    public StudySeat toStudySeat() {
        return StudySeat.of(id, seatNumber, occupied);
    }

    public StudySeat toStudySeatReturn() {
        StudySeat studySeat = StudySeat.of(id, seatNumber, occupied);
        putAllSchedulesFromDomainToEntity(studySeat);

        return studySeat;
    }

    private void putAllSchedulesFromDomainToEntity(StudySeat studySeat) {
        schedules.forEach(scheduleEntity -> studySeat.getSchedules().add(
            Schedule.of(
                scheduleEntity.getId(),
                scheduleEntity.getCustomer().toCustomer(),
                scheduleEntity.getStudySeat().toStudySeat(),
                scheduleEntity.getStartedTime(),
                scheduleEntity.getEndTime()
            )
        ));
    }

    public void setScheduleInEntity(final List<Schedule> schedule) {
        schedule.forEach(domainSchedule -> {

            StudySeatEntity studySeatEntity = toStudySeatEntity(domainSchedule.getStudySeat());
            CustomerEntity customerEntity = toCustomerEntity(domainSchedule.getCustomer());

            ScheduleEntity scheduleEntity = ScheduleEntity.of(
                domainSchedule.getId(),
                customerEntity,
                studySeatEntity,
                domainSchedule.getStartedTime(),
                domainSchedule.getEndTime(),
                domainSchedule.getState()
            );

            scheduleEntity.getCustomer().setScheduleFromDomainToEntity(scheduleEntity);

            schedules.add(scheduleEntity);
        });
    }

//    private CustomerEntity toCustomerEntity(Customer customer) {
//        CustomerEntity customerEntity = CustomerEntity.of(
//            customer.getId(),
//            customer.getName(),
//            customer.getEmail(),
//            customer.getPassword(),
//            customer.getTel()
//        );
//
//        schedules.forEach(scheduleDomain -> customerEntity.getSchedules().add(scheduleDomain));
//
//        return customerEntity;
//    }
}
