package com.example.sms.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sms.Models.Project;
import com.example.sms.Repositories.ProjectRepository;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
@Service
public class ProjectService {
    @Autowired
    private ProjectRepository repo;
    public List<Project> getAllProjects(){
        return repo.findAll();
    }
    public Optional<Project> getProjectById (Integer id){
        return repo.findById(id);
    }
    public Project addProject(Project project){
        return repo.save(project);
    }
public Project updateProject(Project oldProject, Integer id) {
    return repo.findById(id)
        .map(existingProject -> {
            // Update only non-null fields from oldProject
            if (oldProject.getProjectName() != null) {
                existingProject.setProjectName(oldProject.getProjectName());
            }
            if (oldProject.getDescription() != null) {
                existingProject.setDescription(oldProject.getDescription());
            }
            
            return repo.save(existingProject);
        })
        .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + id));
}
}
