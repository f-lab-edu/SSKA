package com.skka.adaptor.controller.studyseat;

import com.skka.adaptor.controller.studyseat.webrequest.CommandChangeStudyTimeWebRequestV1;
import com.skka.adaptor.controller.studyseat.webrequest.CommandMoveSeatWebRequestV1;
import com.skka.application.studyseat.StudySeatService;
import com.skka.application.studyseat.dto.ChangeStudyTimeRequest;
import com.skka.application.studyseat.dto.MoveSeatRequest;
import com.skka.application.studyseat.dto.ReserveSeatRequest;
import com.skka.application.studyseat.response.CommandCancelScheduleResponse;
import com.skka.application.studyseat.response.CommandChangeStudyTimeResponse;
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

    @PostMapping(value = "/seats/{studySeatId}")
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

    @PutMapping(value = "/seats/{studySeatId}/schedules/{scheduleId}")
    public ResponseEntity<CommandMoveSeatResponse> moveSeat(
        final CommandMoveSeatWebRequestV1 command,
        @PathVariable final long studySeatId,
        @PathVariable final long scheduleId
    ) {
        MoveSeatRequest commandService = new MoveSeatRequest(
            command.getCustomerId(),
            command.getStartedTime(),
            command.getEndTime(),
            command.getMovingStudySeatId()
        );

        return ResponseEntity.ok(customerService.moveSeat(
            commandService,
            studySeatId,
            scheduleId
        ));
    }

    @PutMapping(value = "seats/{studySeatId}/schedules/{scheduleId}/adjustment")
    public ResponseEntity<CommandChangeStudyTimeResponse> changeStudyTime(
        final CommandChangeStudyTimeWebRequestV1 command,
        @PathVariable final long scheduleId,
        @PathVariable final long studySeatId
    ) {
        ChangeStudyTimeRequest commandService = new ChangeStudyTimeRequest(
            command.getCustomerId(),
            command.getStartedTime(),
            command.getChangingEndTime()
        );

        return ResponseEntity.ok(customerService.changeStudyTime(
            commandService,
            scheduleId,
            studySeatId
        ));
    }

    @PutMapping(value = "seats/{studySeatId}/schedules/{scheduleId}/customers/{customerId}")
    public ResponseEntity<CommandCancelScheduleResponse> cancelSchedule(
        @PathVariable final long studySeatId,
        @PathVariable final long scheduleId,
        @PathVariable final long customerId
    ) {
        return ResponseEntity.ok(customerService.cancelSchedule(
            studySeatId, scheduleId, customerId
        ));
    }
}
