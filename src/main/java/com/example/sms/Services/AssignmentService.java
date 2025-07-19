package com.example.sms.Services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sms.Models.Assignment;
import com.example.sms.Repositories.AssignmentRepository;
import java.util.Optional;
@Service
public class AssignmentService {
    @Autowired
    private AssignmentRepository repository;

    public Assignment addAssignment(Assignment assignment){
        return repository.save(assignment);
    }
    public List<Assignment> getAllAssignments(){
        return repository.findAll();
    }
    public Optional<Assignment> getAssignmentById(Integer ID){
        return repository.findById(ID);
    }
    public List<Assignment> getAssignmentsByStatus(String status){
        return repository.getAssignmentsByStatus(status);
    }
} 
