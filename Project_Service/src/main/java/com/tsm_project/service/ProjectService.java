package com.tsm_project.service;

import com.tsm_project.dto.ProjectResponse;
import com.tsm_project.entity.Project;

import java.util.List;

public interface ProjectService {
    ProjectResponse createNewProject(Project project);
    List<ProjectResponse> getAllProjects();
    ProjectResponse getProjectById(String id);
    ProjectResponse updateProjectById(String id, Project project);
    void deleteProjectById(String id);

    interface TaskService {
    }
}
