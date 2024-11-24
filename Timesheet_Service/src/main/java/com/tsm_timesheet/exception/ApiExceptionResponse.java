package com.tsm_timesheet.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class ApiExceptionResponse {
    private int status;
    private String message;
    private String details;
}
