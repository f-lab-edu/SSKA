package com.skka.adaptor.controller;

import com.skka.application.customer.CustomerService;
import com.skka.application.customer.dto.CommandMoveSeat;
import com.skka.application.customer.dto.CommandReserveSeat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping(value = "/reservation")
    public ResponseEntity reserveSeat(CommandReserveSeat seat) {
        return ResponseEntity.ok(customerService.reserveSeat(seat));
    }

    @PatchMapping(value = "/reservation")
    public ResponseEntity moveSeat(CommandMoveSeat command) {
        return ResponseEntity.ok(customerService.moveSeat(command));
    }
}
