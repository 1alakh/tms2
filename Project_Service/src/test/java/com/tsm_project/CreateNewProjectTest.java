package com.tsm_project;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsm_project.controller.ProjectController;
import com.tsm_project.dto.ProjectResponse;
import com.tsm_project.service.ProjectService;
import com.tsm_project.entity.Project;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Create New Project Test")
@WebMvcTest(ProjectController.class)
public class CreateNewProjectTest {
    private static final String API_PATH = "/api/v1/projects";

    private final MockMvc mockMvc;

    @MockBean
    private final ProjectService projectService;

    private final ObjectMapper objectMapper;

    @Autowired
    CreateNewProjectTest(MockMvc mockMvc, ProjectService projectService, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.projectService = projectService;
        this.objectMapper = objectMapper;
    }

    @ParameterizedTest
    @CsvSource({
            "Project 1, description 1, 2024-01-01, 2024-12-31",
            "Project 2, description 2, 2024-02-01, 2024-11-30"
    })
    @WithMockUser
    @DisplayName("Manager Should be able to Create new Project")
    void should_Create_New_Project(String name, String description, String startDateStr, String endDateStr) throws Exception {

        LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
        Project project = new Project();
        project.setId("1234");
        project.setName(name);
        project.setDescription(description);
        project.setStartDate(startDate);
        project.setEndDate(endDate);

        ProjectResponse response = new ProjectResponse(project.getId(), project.getName(), project.getDescription(), project.getStartDate(), project.getEndDate());

        BDDMockito.given(projectService.createNewProject(any(Project.class))).willReturn(response);

        // Act
        var resultActions = mockMvc.perform(MockMvcRequestBuilders.post(API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(project))
                .with(csrf()));

        // Assert
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(response.name()))
                .andExpect(jsonPath("$.projectId").value(response.projectId()));
    }
}
