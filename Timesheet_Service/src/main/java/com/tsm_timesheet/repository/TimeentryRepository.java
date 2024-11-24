package com.tsm_timesheet.repository;

import com.tsm_timesheet.entity.TimeEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeentryRepository extends JpaRepository<TimeEntry, String> {
}
