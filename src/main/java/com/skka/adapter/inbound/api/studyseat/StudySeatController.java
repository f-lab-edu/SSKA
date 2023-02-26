package com.skka.adapter.inbound.api.studyseat;

import com.skka.adapter.inbound.api.studyseat.webrequest.CommandChangeStudyTimeWebRequestV1;
import com.skka.adapter.inbound.api.studyseat.webrequest.CommandCheckoutScheduleWebRequestV1;
import com.skka.application.studyseat.StudySeatService;
import com.skka.application.studyseat.dto.ChangeStudyTimeRequest;
import com.skka.application.studyseat.dto.CheckoutScheduleRequest;
import com.skka.application.studyseat.dto.ReserveSeatRequest;
import com.skka.application.studyseat.response.CommandCheckOutScheduleResponse;
import com.skka.application.studyseat.response.CommandChangeStudyTimeResponse;
import com.skka.application.studyseat.response.CommandReserveSeatResponse;
import com.skka.adapter.inbound.api.studyseat.webrequest.CommandReserveSeatWebRequestV1;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StudySeatController {

    private final StudySeatService customerService;

    @PostMapping(value = "/seats/{studySeatId}")
    public ResponseEntity<CommandReserveSeatResponse> reserveSeat(
        @RequestBody final CommandReserveSeatWebRequestV1 command,
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
    public ResponseEntity<Object> extractSchedule(
        @PathVariable final long studySeatId,
        @PathVariable final long scheduleId
    ) {
        customerService.extractSchedule(
            studySeatId,
            scheduleId
        );

        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "seats/{studySeatId}/schedules/{scheduleId}")
    public ResponseEntity<CommandChangeStudyTimeResponse> updateSchedule(
        @RequestBody final CommandChangeStudyTimeWebRequestV1 command,
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

    @PatchMapping(value = "seats/{studySeatId}/schedules/{scheduleId}")
    public ResponseEntity<CommandCheckOutScheduleResponse> checkoutSchedule(
        @RequestBody final CommandCheckoutScheduleWebRequestV1 command,
        @PathVariable final long studySeatId,
        @PathVariable final long scheduleId
    ) {
        CheckoutScheduleRequest commandService = new CheckoutScheduleRequest(
            command.getScheduleState()
        );

        return ResponseEntity.ok(customerService.checkoutSchedule(
            commandService, studySeatId, scheduleId
        ));
    }
}
