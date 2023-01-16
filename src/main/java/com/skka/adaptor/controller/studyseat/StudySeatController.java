package com.skka.adaptor.controller.studyseat;

import com.skka.adaptor.controller.studyseat.webrequest.CommandChangeStudyTimeWebRequestV1;
import com.skka.application.studyseat.StudySeatService;
import com.skka.application.studyseat.dto.ChangeStudyTimeRequest;
import com.skka.application.studyseat.dto.ReserveSeatRequest;
import com.skka.application.studyseat.response.CommandChangeStudyTimeResponse;
import com.skka.application.studyseat.response.CommandMoveSeatResponse;
import com.skka.application.studyseat.response.CommandReserveSeatResponse;
import com.skka.adaptor.controller.studyseat.webrequest.CommandReserveSeatWebRequestV1;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @DeleteMapping(value = "/seats/{studySeatId}/schedules/{scheduleId}")
    public ResponseEntity<CommandMoveSeatResponse> extractSchedule(
        @PathVariable final long studySeatId,
        @PathVariable final long scheduleId
    ) {
        return ResponseEntity.ok(customerService.extractSchedule(
            studySeatId,
            scheduleId
        ));
    }

    @PutMapping(value = "seats/{studySeatId}/schedules/{scheduleId}")
    public ResponseEntity<CommandChangeStudyTimeResponse> changeStudyTime(
        final CommandChangeStudyTimeWebRequestV1 command,
        @PathVariable final long studySeatId,
        @PathVariable final long scheduleId
    ) {
        ChangeStudyTimeRequest commandService = new ChangeStudyTimeRequest(
            command.getCustomerId(),
            command.getChangingStartedTime(),
            command.getChangingEndTime()
        );

        return ResponseEntity.ok(customerService.changeStudyTime(
            commandService,
            studySeatId,
            scheduleId
        ));
    }
}
