package com.tsm_project.dto;

import java.time.LocalDate;

public record ProjectResponse(String projectId, String name, String description, LocalDate startDate, LocalDate endDate) {
}
