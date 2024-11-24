package com.tsm_timesheet.service.serviceImpl;

import com.tsm_timesheet.dto.TimeentryResponse;
import com.tsm_timesheet.entity.TimeEntry;
import com.tsm_timesheet.repository.TimeentryRepository;
import com.tsm_timesheet.service.TimeentryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TimeentryServiceImpl implements TimeentryService {

    private final TimeentryRepository timeentryRepository;

    @Autowired
    TimeentryServiceImpl(TimeentryRepository timeentryRepository){
        this.timeentryRepository = timeentryRepository;
    }

    @Transactional
    public TimeentryResponse createNewTimeentry(TimeEntry project){
        TimeEntry newTimeentry = timeentryRepository.save(project);
        return new TimeentryResponse(newTimeentry.getId(), newTimeentry.getDescription());
    }

    @Override
    public List<TimeEntry> getAllTimeEntries() {
        return timeentryRepository.findAll();
    }
}
