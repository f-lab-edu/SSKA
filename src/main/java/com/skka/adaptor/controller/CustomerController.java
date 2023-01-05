package com.skka.adaptor.controller;

import com.skka.application.customer.CustomerService;
import com.skka.application.customer.dto.ReserveSeatRequest;
import com.skka.application.customer.response.CommandReserveSeatResponse;
import com.skka.application.customer.webrequest.CommandReserveSeatWebRequestV1;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

}
