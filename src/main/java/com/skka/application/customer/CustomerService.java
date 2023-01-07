package com.skka.application.customer;

import com.skka.application.customer.dto.AddStudyTimeRequest;
import com.skka.application.customer.dto.MoveSeatRequest;
import com.skka.application.customer.response.CommandAddStudyTimeResponse;
import com.skka.application.customer.response.CommandCancelScheduleResponse;
import com.skka.application.customer.response.CommandMoveSeatResponse;
import com.skka.application.customer.response.CommandReserveSeatResponse;
import com.skka.application.customer.dto.ReserveSeatRequest;
import com.skka.domain.customer.Customer;
import com.skka.domain.customer.repository.CustomerRepository;
import com.skka.domain.schedule.Schedule;
import com.skka.domain.schedule.repository.ScheduleRepository;
import com.skka.domain.studyseat.StudySeat;
import com.skka.domain.studyseat.repository.StudySeatRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final StudySeatRepository studySeatRepository;
    private final ScheduleRepository scheduleRepository;


    private String success = "success";

    @Transactional
    public CommandReserveSeatResponse reserveSeat(final ReserveSeatRequest command, final long studySeatId) {
        Customer customer = findByCustomerId(command.getCustomerId());
        StudySeat studySeat = findByStudySeatId(studySeatId);
        studySeat.isReservable(command.getStartedTime(), command.getEndTime());

        Schedule schedule = Schedule.of(
            customer,
            studySeat,
            command.getStartedTime(),
            command.getEndTime()
        );

        scheduleRepository.save(schedule);

        return new CommandReserveSeatResponse(success, studySeatId);
    }

    private Customer findByCustomerId(final long customerId) {
        return customerRepository.findById(customerId)
            .orElseThrow(() -> new IllegalArgumentException("고객을 찾지 못했습니다."));
    }

    private StudySeat findByStudySeatId(final long studySeatId) {
        return studySeatRepository.findById(studySeatId)
            .orElseThrow(() -> new IllegalArgumentException("좌석을 찾지 못했습니다."));
    }

    @Transactional
    public CommandMoveSeatResponse moveSeat(final MoveSeatRequest command, final long movingStudySeatId) {

        Customer customer = findByCustomerId(command.getCustomerId());
        StudySeat movingStudySeat = findByStudySeatId(movingStudySeatId);
        movingStudySeat.isReservable(command.getStartedTime(), command.getEndTime());

        Schedule foundSchedule = findScheduleByStartAndEndTime(
            command.getStartedTime(), command.getEndTime()
        );

        foundSchedule.updateStudySeat(customer, movingStudySeat);

        scheduleRepository.save(foundSchedule);
        return new CommandMoveSeatResponse(success, movingStudySeatId);
    }

    private Schedule findScheduleByStartAndEndTime(
        final LocalDateTime startedTime,
        final LocalDateTime endTime
    ) {
        return scheduleRepository.findScheduleByStartedTimeAndEndTime(startedTime, endTime);
    }

    @Transactional
    public CommandAddStudyTimeResponse addStudyTime(
        final AddStudyTimeRequest command,
        final long studySeatId,
        final long scheduleId
    ) {
        StudySeat studySeat = findByStudySeatId(studySeatId);
        studySeat.isReservable(
            command.getEndTime(),
            command.getEndTime().plusHours(command.getPlusHour())
        );

        Schedule schedule = findByScheduleId(scheduleId);

        schedule.updateEndTime(command.getEndTime().plusHours(command.getPlusHour()));

        scheduleRepository.save(schedule);
        return new CommandAddStudyTimeResponse(success, 1L, command.getPlusHour());
    }

    private Schedule findByScheduleId(final long scheduleId) {
        return scheduleRepository.findById(scheduleId)
            .orElseThrow(() -> new IllegalArgumentException("스케줄을 찾지 못했습니다."));
    }

    @Transactional
    public CommandCancelScheduleResponse cancelSchedule(
        final long scheduleId,
        final long customerId
    ) {
        Schedule schedule = findByScheduleId(scheduleId);

        schedule.checkIfRightCustomer(customerId);

        schedule.cancel();

        return new CommandCancelScheduleResponse(success, scheduleId);
    }
}
