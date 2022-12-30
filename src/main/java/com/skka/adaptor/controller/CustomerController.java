package com.skka.adaptor.controller;

import com.skka.application.customer.CustomerService;
import com.skka.application.customer.dto.CommandReserveSeat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping(value = "/customer")
    public ResponseEntity reserveSeat(CommandReserveSeat seat) {
        return ResponseEntity.ok(customerService.reserveSeat(seat));
    }
}
