package com.skka.adaptor.controller.navercloudplatform;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NcpController extends NcpService {

    private String list_url = "/api/v1/project";

    @GetMapping(value = "/ncp/list")
    public ResponseEntity<Object> lookForList() throws Exception {

        String timeStamp = String.valueOf(System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.OK).body(ncpLookForList(
            timeStamp, HttpMethod.GET, list_url
        ));
    }

    @PostMapping(value = "/ncp/building_trigger/{projectId}")
    public ResponseEntity<Object> triggerBuild(
        @PathVariable final String projectId
    ) throws Exception {

        String timeStamp = String.valueOf(System.currentTimeMillis());
        String build_trigger_url = "/api/v1/project/" + projectId + "/build";
        return ResponseEntity.status(HttpStatus.OK).body(ncpHookBuildingTrigger(
            timeStamp, HttpMethod.POST, build_trigger_url
        ));
    }
}