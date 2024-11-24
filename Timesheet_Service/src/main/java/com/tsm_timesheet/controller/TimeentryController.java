package com.tsm_timesheet.controller;


import com.tsm_timesheet.dto.TimeentryResponse;
import com.tsm_timesheet.entity.TimeEntry;
import com.tsm_timesheet.service.TimeentryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/timeentries")
public class TimeentryController {

    private final TimeentryService timeentryService;

    @Autowired
    TimeentryController(TimeentryService timeentryService){
        this.timeentryService = timeentryService;
    }

    @PostMapping
    public ResponseEntity<TimeentryResponse> createNewTimeentry(@Valid @RequestBody TimeEntry timeEntry){
        TimeentryResponse response = timeentryService.createNewTimeentry(timeEntry);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping
    public List<TimeEntry> getAllTimeEntries(){
        return timeentryService.getAllTimeEntries();
    }
}
