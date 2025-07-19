package com.example.sms.Controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.sms.Models.Assignment;
import com.example.sms.Services.AssignmentService;


@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/v1/assignments")
public class AssignmentController {
    @Autowired
    private AssignmentService service;
    @GetMapping
    public List<Assignment> getAll(){
        return service.getAllAssignments();
    }
    @GetMapping("/{id}")
    public Optional<Assignment> getByID(@PathVariable Integer id){
        return service.getAssignmentById(id);
    }
    @PostMapping
    public ResponseEntity<String> add(@RequestBody Assignment assignment){
        service.addAssignment(assignment);
        return ResponseEntity.ok("Assignment is added successfully");
    }
    @GetMapping("/by_status/{status}")
    public List<Assignment> getAssignmentsByStatus(@PathVariable String status){
        return service.getAssignmentsByStatus(status);
    }
}
