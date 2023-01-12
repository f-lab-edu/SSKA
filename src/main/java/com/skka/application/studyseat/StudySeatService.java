package com.skka.application.studyseat;

import static com.skka.adaptor.common.exception.ErrorType.INVALID_SCHEDULE_RESERVATION_ALREADY_OCCUPIED;
import static com.skka.adaptor.util.Util.check;

import com.skka.application.studyseat.dto.ReserveSeatRequest;
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
        Customer customer = findByCustomerId(command.getCustomerId());
        StudySeat studySeat = findByStudySeatId(studySeatId);

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

    private StudySeat findByStudySeatId(final long studySeatId) {
        return studySeatRepository.findById(studySeatId)
            .orElseThrow(() -> new IllegalArgumentException("좌석을 찾지 못했습니다."));
    }
}
