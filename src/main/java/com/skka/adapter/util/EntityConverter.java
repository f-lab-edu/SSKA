package com.skka.adapter.util;

import com.skka.adapter.outbound.jpa.customer.CustomerEntity;
import com.skka.adapter.outbound.jpa.studyseat.StudySeatEntity;
import com.skka.adapter.outbound.jpa.studyseat.schedule.ScheduleEntity;
import com.skka.domain.customer.Customer;
import com.skka.domain.studyseat.StudySeat;

public class EntityConverter {

    public static StudySeatEntity toStudySeatEntity(StudySeat studySeat) {
        return StudySeatEntity.of(
            studySeat.getId(),
            studySeat.getSeatNumber(),
            studySeat.isOccupied()
        );
    }

    public static StudySeatEntity toStudySeatEntity(StudySeatEntity studySeatEntity) {
        return StudySeatEntity.of(
            studySeatEntity.getId(),
            studySeatEntity.getSeatNumber(),
            studySeatEntity.isOccupied()
        );
    }

    public static CustomerEntity toCustomerEntity(Customer customer) {
        CustomerEntity customerEntity = CustomerEntity.of(
            customer.getId(),
            customer.getName(),
            customer.getEmail(),
            customer.getPassword(),
            customer.getTel()
        );

        customer.getSchedules().forEach(scheduleDomain -> customerEntity.getSchedules().add(
            ScheduleEntity.of(
                scheduleDomain.getId(),
                customerEntity,
                toStudySeatEntity(scheduleDomain.getStudySeat()),
                scheduleDomain.getStartedTime(),
                scheduleDomain.getEndTime(),
                scheduleDomain.getState()
            )
        ));

        return customerEntity;
    }

    public static StudySeatEntity toStudySeatEntityWithScheduleEntity(StudySeat studySeat) {
        StudySeatEntity studySeatEntity = StudySeatEntity.of(
            studySeat.getId(),
            studySeat.getSeatNumber(),
            studySeat.isOccupied()
        );

        studySeatEntity.setScheduleInEntity(studySeat.getSchedules());

        return studySeatEntity;
    }
}
