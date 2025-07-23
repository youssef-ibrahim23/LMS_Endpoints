package com.example.sms.Controllers;

import com.example.sms.Models.Project;
import com.example.sms.Services.ProjectService;
import com.example.sms.Services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {
    @Autowired
    private ProjectService service;
    
    // Get all projects
    @GetMapping
    public Map<String, Object> getAllProjects() {
        try {
            List<Project> projects = service.getAllProjects();
            return ResponseService.createSuccessResponse("Projects retrieved successfully", projects);
        } catch (Exception e) {
            return ResponseService.createErrorResponse(e.getMessage());
        }
    }
    
    // Get a single project by ID
    @GetMapping("/{id}")
    public Map<String, Object> getProjectById(@PathVariable Integer id) {
        try {
            Optional<Project> project = service.getProjectById(id);
            return project.map(value -> ResponseService.createSuccessResponse("Project retrieved successfully", value))
                    .orElseGet(() -> ResponseService.createErrorResponse("Project not found"));
        } catch (Exception e) {
            return ResponseService.createErrorResponse(e.getMessage());
        }
    }
    
    // Create a new project
    @PostMapping
    public Map<String, Object> createProject(@RequestBody Project project) {
        try {
            Project newProject = service.addProject(project);
            return ResponseService.createSuccessResponse("Project created successfully", newProject);
        } catch (Exception e) {
            return ResponseService.createErrorResponse(e.getMessage());
        }
    }
    
    // Update an existing project
    @PutMapping("/{id}")
    public Map<String, Object> updateProject(@PathVariable Integer id, @RequestBody Project project) {
        try {
            Project updatedProject = service.updateProject(project, id);
            return ResponseService.createSuccessResponse("Project updated successfully", updatedProject);
        } catch (Exception e) {
            return ResponseService.createErrorResponse(e.getMessage());
        }
    }
}