package com.tsm_project.controller;

import com.tsm_project.dto.ProjectResponse;
import com.tsm_project.entity.Project;
import com.tsm_project.service.ProjectService;
import jakarta.validation.Valid;
import jakarta.ws.rs.PATCH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    ProjectController(ProjectService projectService){
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> createNewProject(@Valid @RequestBody Project project){
        ProjectResponse response = projectService.createNewProject(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public List<ProjectResponse> getAllProjects(){
        return projectService.getAllProjects();
    }

    @GetMapping("/{id}")
    public ProjectResponse getProjectById(@PathVariable String id){
        return projectService.getProjectById(id);
    }

    @PatchMapping("/{id}")
    public ProjectResponse updateProjectById(@PathVariable String id, @RequestBody Project project){
        return projectService.updateProjectById(id, project);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProjectById(@PathVariable String id){
        projectService.deleteProjectById(id);
        return ResponseEntity.ok("Project with Id "+ id+" deleted successfully.");
    }
}
