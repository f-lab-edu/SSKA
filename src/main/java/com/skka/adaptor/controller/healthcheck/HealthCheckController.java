package com.skka.adaptor.controller.healthcheck;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping(value = "/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.status(HttpStatus.OK).body("healthy");
    }
}
