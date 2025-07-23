package com.example.sms.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.sms.Models.AssignmentSubmission;
import com.example.sms.Services.AssignmentSubmissionService;
import com.example.sms.Services.ResponseService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/assignment-submissions")
@CrossOrigin(origins = "*")
public class AssignmentSubmissionController {

    @Autowired
    private AssignmentSubmissionService service;

    @GetMapping
    public ResponseEntity<?> getAllSubmissions() {
        try {
            List<AssignmentSubmission> submissions = service.getAllSubmissions();
            if (submissions.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(ResponseService.createErrorResponse("No submissions found"));
            }
            return ResponseEntity.ok(submissions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseService.createErrorResponse("Failed to retrieve submissions: " + e.getMessage()));
        }
    }

@GetMapping("/{id}")
public ResponseEntity<?> getSubmissionById(@PathVariable Integer id) {
    try {
        // Validate input
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Invalid submission ID"));
        }

        // Get submission from service
        Optional<AssignmentSubmission> submission = service.getSubmissionById(id);
        
        // Return appropriate response
        if (submission.isPresent()) {
            return ResponseEntity.ok(submission.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseService.createErrorResponse("Submission not found with ID: " + id));
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to retrieve submission: " + e.getMessage()));
    }
}
    @PostMapping
    public ResponseEntity<?> submitAssignment(@RequestBody AssignmentSubmission submission) {
        try {
            if (submission == null) {
                return ResponseEntity.badRequest()
                        .body(ResponseService.createErrorResponse("Submission data is required"));
            }
            if (submission.getAssignment() == null) {
                return ResponseEntity.badRequest()
                        .body(ResponseService.createErrorResponse("Assignment ID is required"));
            }
            if (submission.getStudent() == null) {
                return ResponseEntity.badRequest()
                        .body(ResponseService.createErrorResponse("Student ID is required"));
            }
            if (submission.getSubmitAt() == null) {
                return ResponseEntity.badRequest()
                        .body(ResponseService.createErrorResponse("Submission date is required"));
            }

            AssignmentSubmission createdSubmission = service.submitAssignment(submission);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ResponseService.createSuccessResponse("Submission created successfully", createdSubmission));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseService.createErrorResponse("Failed to create submission: " + e.getMessage()));
        }
    }
}