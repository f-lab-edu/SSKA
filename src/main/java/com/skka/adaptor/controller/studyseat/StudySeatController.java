package com.skka.adaptor.controller.studyseat;

import com.skka.adaptor.controller.studyseat.webrequest.CommandMoveSeatWebRequestV1;
import com.skka.application.studyseat.StudySeatService;
import com.skka.application.studyseat.dto.MoveSeatRequest;
import com.skka.application.studyseat.dto.ReserveSeatRequest;
import com.skka.application.studyseat.response.CommandMoveSeatResponse;
import com.skka.application.studyseat.response.CommandReserveSeatResponse;
import com.skka.adaptor.controller.studyseat.webrequest.CommandReserveSeatWebRequestV1;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StudySeatController {

    private final StudySeatService customerService;

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

        return ResponseEntity.status(HttpStatus.OK).body(customerService.reserveSeat(
            commandService,
            studySeatId
        ));
    }

    @PutMapping(value = "/seat/{studySeatId}")
    public ResponseEntity<CommandMoveSeatResponse> moveSeat(
        final CommandMoveSeatWebRequestV1 command,
        @PathVariable final long studySeatId
    ) {
        MoveSeatRequest commandService = new MoveSeatRequest(
            command.getCustomerId(),
            command.getStartedTime(),
            command.getEndTime(),
            command.getMovingStudySeatId()
        );

        return ResponseEntity.ok(customerService.moveSeat(
            commandService,
            studySeatId
        ));
    }
}
