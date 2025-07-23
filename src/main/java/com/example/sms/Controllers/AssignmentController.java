package com.example.sms.Controllers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sms.Models.Assignment;
import com.example.sms.Services.AssignmentService;
import com.example.sms.Services.ResponseService;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/v1/assignments")
public class AssignmentController {
    
    @Autowired
    private AssignmentService service;

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<Assignment> assignments = service.getAllAssignments();
            return ResponseEntity.ok(assignments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to retrieve assignments"));
        }
    }

@GetMapping("/{id}")
public ResponseEntity<?> getByID(@PathVariable Integer id) {
    try {
        // Validate input
        if (id == null || id <= 0) {
            Map<String, Object> errorResponse = ResponseService.createErrorResponse("Invalid assignment ID");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        // Fetch assignment
        Optional<Assignment> assignmentOptional = service.getAssignmentById(id);
        
        // Handle the Optional explicitly
        if (assignmentOptional.isPresent()) {
            Assignment assignment = assignmentOptional.get();
            return ResponseEntity.ok(assignment);
        } else {
            Map<String, Object> errorResponse = ResponseService.createErrorResponse(
                "Assignment not found with ID: " + id
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        
    } catch (Exception e) {
        Map<String, Object> errorResponse = ResponseService.createErrorResponse(
            "Failed to retrieve assignment: " + e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(errorResponse);
    }
}

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Assignment assignment) {
        try {
            if (assignment == null) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Assignment data is required"));
            }
            if (assignment.getTitle() == null || assignment.getTitle().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Assignment title is required"));
            }
            if (assignment.getDescription() == null || assignment.getDescription().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Assignment description is required"));
            }
            if (assignment.getDeadline() == null) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Dead line is required"));
            }

            service.addAssignment(assignment);
            return ResponseEntity.ok(ResponseService.createSuccessResponse("Assignment added successfully", assignment));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to add assignment"));
        }
    }

    @GetMapping("/by_status/{status}")
    public ResponseEntity<?> getAssignmentsByStatus(@PathVariable String status) {
        try {
            if (status == null || status.isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Status parameter is required"));
            }

            List<Assignment> assignments = service.getAssignmentsByStatus(status);
            if (assignments.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseService.createErrorResponse("No assignments found with status: " + status));
            }
            return ResponseEntity.ok(assignments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to retrieve assignments by status"));
        }
    }

    @GetMapping("/pending/{id}")
    public List<Assignment> findOverdueAssignmentsByStudentId(@PathVariable Integer id){
        return service.findOverdueAssignmentsByStudentId(id);
    }
}