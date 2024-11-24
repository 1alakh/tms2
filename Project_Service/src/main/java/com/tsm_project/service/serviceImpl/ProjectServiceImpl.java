package com.tsm_project.service.serviceImpl;

import com.tsm_project.dto.ProjectResponse;
import com.tsm_project.entity.Project;
import com.tsm_project.repository.ProjectRepository;
import com.tsm_project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Transactional
    public ProjectResponse createNewProject(Project project) {
        Project newProject = projectRepository.save(project);
        return new ProjectResponse(newProject.getId(), newProject.getName(), newProject.getDescription(), newProject.getStartDate(), newProject.getEndDate());
    }

    @Override
    public List<ProjectResponse> getAllProjects() {
        List<Project> projects = projectRepository.findAll();

        return projects.stream()
                .map(this::convertToProjectResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectResponse getProjectById(String id) {
        Optional<Project> project = projectRepository.findById(id);
        return new ProjectResponse(project.get().getId(), project.get().getName(), project.get().getDescription(), project.get().getStartDate(), project.get().getEndDate());
    }

    @Override
    public ProjectResponse updateProjectById(String id, Project newProject) {
        Optional<Project> projectOptional = projectRepository.findById(id);
        Project project = projectOptional.get();
        if (newProject.getName() != null) {
            project.setName(newProject.getName());
        }
        if (newProject.getDescription() != null) {
            project.setDescription(newProject.getDescription());
        }
        if (newProject.getStartDate() != null) {
            project.setStartDate(newProject.getStartDate());
        }
        if (newProject.getEndDate() != null) {
            project.setEndDate(newProject.getEndDate());
        }

        Project savedProject = projectRepository.save(project);
        return new ProjectResponse(savedProject.getId(), savedProject.getName(), savedProject.getDescription(), savedProject.getStartDate(), savedProject.getEndDate());

    }

    @Override
    public void deleteProjectById(String id) {
        projectRepository.deleteById(id);
    }

    private ProjectResponse convertToProjectResponse(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getStartDate(),
                project.getEndDate()
        );
    }

}
