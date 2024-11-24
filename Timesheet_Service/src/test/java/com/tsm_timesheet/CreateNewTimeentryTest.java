package com.tsm_timesheet;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.tsm_timesheet.controller.TimeentryController;
import com.tsm_timesheet.dto.TimeentryResponse;
import com.tsm_timesheet.entity.TimeEntry;
import com.tsm_timesheet.service.TimeentryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Create New Timesheet Entry Test")
@WebMvcTest(TimeentryController.class)
public class CreateNewTimeentryTest {
	private static final String API_PATH = "/api/v1/timeentries";

	private final MockMvc mockMvc;

	@MockBean
	private final TimeentryService timeentryService;

	private final ObjectMapper objectMapper;

	@Autowired
	CreateNewTimeentryTest(MockMvc mockMvc, TimeentryService timeentryService, ObjectMapper objectMapper) {
		this.mockMvc = mockMvc;
		this.timeentryService = timeentryService;
		this.objectMapper = objectMapper;
	}

	@ParameterizedTest
	@CsvSource({
			"UserId1, taskId1, 2024-01-01, 3.5, description 1, 3",
			"UserId2, taskId2, 2024-01-03, 4.5, description 2, 5"
	})
	@WithMockUser
	@DisplayName("Should be able to create New TimeEntry")
	void should_Create_New_Timeentry(String user, String task, String dateStr, String hours, String description, String status) throws Exception {

		LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);

		TimeEntry timeEntry = new TimeEntry();
		timeEntry.setId("1234");
		timeEntry.setUser(user);
		timeEntry.setDescription(description);
		timeEntry.setTask(task);
		timeEntry.setDate(date);
		timeEntry.setHours(new BigDecimal(hours));
		timeEntry.setStatus(Integer.parseInt(status));


		TimeentryResponse response = new TimeentryResponse(timeEntry.getId(), timeEntry.getDescription());

		BDDMockito.given(timeentryService.createNewTimeentry(any(TimeEntry.class))).willReturn(response);

		// Act
		var resultActions = mockMvc.perform(MockMvcRequestBuilders.post(API_PATH)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(timeEntry))
				.with(csrf()));

		// Assert
		resultActions.andExpect(status().isCreated())
				.andExpect(jsonPath("$.description").value(response.description()))
				.andExpect(jsonPath("$.timeentryId").value(response.timeentryId()));
	}
}
