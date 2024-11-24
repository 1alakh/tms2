package com.tsm_timesheet.service;

import com.tsm_timesheet.dto.TimeentryResponse;
import com.tsm_timesheet.entity.TimeEntry;

import java.util.List;

public interface TimeentryService {
    TimeentryResponse createNewTimeentry(TimeEntry timeEntry);
    List<TimeEntry> getAllTimeEntries();

}
