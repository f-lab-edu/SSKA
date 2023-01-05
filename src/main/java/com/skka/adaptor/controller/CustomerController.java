package com.skka.adaptor.controller;

import com.skka.application.customer.CustomerService;
import com.skka.application.customer.dto.AddStudyTimeRequest;
import com.skka.application.customer.dto.CommandAddStudyTime;
import com.skka.application.customer.dto.MoveSeatRequest;
import com.skka.application.customer.response.CommandAddStudyTimeResponse;
import com.skka.application.customer.response.CommandMoveSeatResponse;
import com.skka.application.customer.webrequest.CommandAddStudyTimeWebRequestV1;
import com.skka.application.customer.webrequest.CommandMoveSeatWebRequestV1;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import com.skka.application.customer.dto.ReserveSeatRequest;
import com.skka.application.customer.response.CommandReserveSeatResponse;
import com.skka.application.customer.webrequest.CommandReserveSeatWebRequestV1;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping(value = "/seat/{studySeatId}")
    public ResponseEntity<CommandReserveSeatResponse> reserveSeat(
        final CommandReserveSeatWebRequestV1 command,
        @PathVariable final long studySeatId
    ) {
        ReserveSeatRequest commandService = new ReserveSeatRequest(
            command.getCustomerId(),
            command.getStartedTime(),
            command.getEndTime()
        );

        return ResponseEntity.ok(customerService.reserveSeat(
            commandService, studySeatId
        ));
    }

    @PatchMapping(value = "/seat/{movingStudySeatId}")
    public ResponseEntity<CommandMoveSeatResponse> moveSeat(
        final CommandMoveSeatWebRequestV1 command,
        @PathVariable final long movingStudySeatId
    ) {
        MoveSeatRequest commandService = new MoveSeatRequest(
            command.getCustomerId(),
            command.getStartedTime(),
            command.getEndTime()
        );

        return ResponseEntity.ok(customerService.moveSeat(commandService, movingStudySeatId));
    }

    @PutMapping(value = "/customer/more_time") // /seat/{studySeatId}/schedule/{scheduleId} 로 바뀔예정
    public ResponseEntity<CommandAddStudyTimeResponse> addTime(
        final CommandAddStudyTimeWebRequestV1 command
    ) {
        AddStudyTimeRequest commandService = new AddStudyTimeRequest(
            command.getCustomerId(),
            command.getStudySeatId(),
            command.getStartedTime(),
            command.getEndTime(),
            command.getPlusHour()
        );

        return ResponseEntity.ok(customerService.addStudyTime(commandService));
    }
}
