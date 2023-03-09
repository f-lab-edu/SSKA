package com.skka.application.studyseat;

import static com.skka.adapter.common.exception.ErrorType.INVALID_SCHEDULE_RESERVATION_ALREADY_OCCUPIED;
import static com.skka.adapter.util.Util.check;

import com.skka.application.studyseat.dto.ChangeStudyTimeRequest;
import com.skka.application.studyseat.dto.CheckoutScheduleRequest;
import com.skka.application.studyseat.dto.ReserveSeatRequest;
import com.skka.application.studyseat.response.CommandCheckOutScheduleResponse;
import com.skka.application.studyseat.response.CommandChangeStudyTimeResponse;
import com.skka.application.studyseat.response.CommandExtractScheduleResponse;
import com.skka.application.studyseat.response.CommandReserveSeatResponse;
import com.skka.domain.customer.Customer;
import com.skka.domain.customer.repository.CustomerRepository;
import com.skka.domain.studyseat.StudySeat;
import com.skka.domain.studyseat.repository.StudySeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudySeatService {

    private final CustomerRepository customerRepository;
    private final StudySeatRepository studySeatRepository;

    private String success = "success";

    @Transactional
    public CommandReserveSeatResponse reserveSeat(final ReserveSeatRequest command, final long studySeatId) {
        StudySeat studySeat = findByStudySeatIdForLock(studySeatId);
        Customer customer = findByCustomerId(command.getCustomerId());

        check(studySeat.isReservable(command.getStartedTime(), command.getEndTime())
            , INVALID_SCHEDULE_RESERVATION_ALREADY_OCCUPIED)
        ;

        studySeat.reserve(
            customer, studySeat,
            command.getStartedTime(), command.getEndTime()
        );

        studySeatRepository.save(studySeat);

        return new CommandReserveSeatResponse(success, studySeatId);
    }

    private Customer findByCustomerId(final long customerId) {
        return customerRepository.findById(customerId)
            .orElseThrow(() -> new IllegalArgumentException("고객을 찾지 못했습니다."));
    }

    private StudySeat findByStudySeatIdForLock(final long studySeatId) {
        return studySeatRepository.findByIdForLock(studySeatId)
            .orElseThrow(() -> new IllegalArgumentException("좌석을 찾지 못했습니다."));
    }

    @Transactional
    public CommandExtractScheduleResponse extractSchedule(
        final long studySeatId,
        final long scheduleId
    ) {
        StudySeat studySeat = findByStudySeatId(studySeatId);
        studySeat.extractScheduleWith(scheduleId);

        studySeatRepository.save(studySeat);

        return new CommandExtractScheduleResponse(success, scheduleId, studySeatId);
    }

    private StudySeat findByStudySeatId(final long studySeatId) {
        return studySeatRepository.findById(studySeatId)
            .orElseThrow(() -> new IllegalArgumentException("좌석을 찾지 못했습니다."));
    }

    @Transactional
    public CommandChangeStudyTimeResponse changeStudyTime(
        final ChangeStudyTimeRequest command,
        final long studySeatId,
        final long scheduleId
    ) {
        StudySeat studySeat = findByStudySeatId(studySeatId);
        studySeat.checkBeneathOfAHour(command.getChangingStartedTime(), command.getChangingEndTime());

        extractSchedule(studySeatId, scheduleId);

        reserveSeat(
            new ReserveSeatRequest(
                command.getCustomerId(),
                command.getChangingStartedTime(),
                command.getChangingEndTime()
            ),
            studySeatId
        );

        studySeatRepository.save(studySeat);

        return new CommandChangeStudyTimeResponse(success, command.getChangingStartedTime(), command.getChangingEndTime());
    }

    @Transactional
    public CommandCheckOutScheduleResponse checkoutSchedule(
        final CheckoutScheduleRequest command,
        final long studySeatId,
        final long scheduleId
    ) {
        StudySeat studySeat = findByStudySeatId(studySeatId);
        studySeat.checkout(scheduleId, command.getScheduleState());

        studySeatRepository.save(studySeat);
        return new CommandCheckOutScheduleResponse(success, scheduleId);
    }
}
