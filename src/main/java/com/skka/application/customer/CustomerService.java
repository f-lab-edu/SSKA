package com.skka.application.customer;

import static com.skka.adaptor.common.exception.ErrorType.INVALID_SCHEDULE_ALREADY_RESERVED;
import static com.skka.adaptor.util.Util.require;

import com.skka.application.customer.dto.CommandReserveSeat;
import com.skka.domain.customer.Customer;
import com.skka.domain.customer.repository.CustomerRepository;
import com.skka.domain.schedule.Schedule;
import com.skka.domain.schedule.repository.ScheduleRepository;
import com.skka.domain.studyseat.StudySeat;
import com.skka.domain.studyseat.repository.StudySeatRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final StudySeatRepository studySeatRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public String reserveSeat(final CommandReserveSeat command) {
        Customer customer = findByCustomerId(command.getCustomerId());
        StudySeat studySeat = findByStudySeatId(command.getSeatNumber());

        checkReservation(command.getStartedTime(), command.getPlusHourToCalculateEndTime(), studySeat.getId());

        Schedule schedule = Schedule.of(
            customer,
            studySeat,
            command.getStartedTime(),
            command.getPlusHourToCalculateEndTime()
        );

        scheduleRepository.save(schedule);

        return command.getSeatNumber() + "번 자리에 " + "예약 되었습니다.";
    }

    private Customer findByCustomerId(final long customerId) {
        return customerRepository.findById(customerId)
            .orElseThrow(() -> new IllegalArgumentException("고객을 찾지 못했습니다."));
    }

    private StudySeat findByStudySeatId(final long studySeatId) {
        return studySeatRepository.findById(studySeatId)
            .orElseThrow(() -> new IllegalArgumentException("좌석을 찾지 못했습니다."));
    }

    private void checkReservation(LocalDateTime startedTime, long plusHours, long studySeatId) {
        List<Schedule> schedules = scheduleRepository.findAllSchedulesByStartedEndTime(
            startedTime,
            startedTime.plusHours(plusHours),
            studySeatId
        );
        require(o -> !schedules.isEmpty(), schedules, INVALID_SCHEDULE_ALREADY_RESERVED);
    }
}
