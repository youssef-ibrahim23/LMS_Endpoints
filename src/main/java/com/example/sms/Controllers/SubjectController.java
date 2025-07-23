package com.example.sms.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sms.Models.Subject;
import com.example.sms.Services.ResponseService;
import com.example.sms.Services.SubjectService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/subjects")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @PostMapping
    public ResponseEntity<?> createSubject(@RequestBody Subject subject) {
        try {
            if (subject == null) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Subject data cannot be null"));
            }

            if (subject.getSubjectName() == null || subject.getSubjectName().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Subject name is required"));
            }

            Subject createdSubject = subjectService.addSubject(subject);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseService.createSuccessResponse("Subject created successfully", createdSubject));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to create subject: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllSubjects() {
        try {
            List<Subject> subjects = subjectService.getSubjects();
            if (subjects.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(ResponseService.createSuccessResponse("No subjects found", null));
            }
            return ResponseEntity.ok(ResponseService.createSuccessResponse("Subjects retrieved successfully", subjects));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to retrieve subjects: " + e.getMessage()));
        }
    }

    @GetMapping("/count")
    public ResponseEntity<?> getSubjectsCount() {
        try {
            Long count = subjectService.getCountOfSubjects();
            return ResponseEntity.ok(ResponseService.createSuccessResponse("Subjects count retrieved successfully", count));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to get subjects count: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editSubject(@PathVariable int id, @RequestBody Subject subject) {
        try {
            if (id <= 0) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Invalid subject ID"));
            }

            if (subject == null) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Subject data cannot be null"));
            }

            if (subject.getSubjectName() == null || subject.getSubjectName().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Subject name is required"));
            }

            int updatedCount = subjectService.updateSubject(id, subject);
            if (updatedCount == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseService.createErrorResponse("Subject not found with ID: " + id));
            }

            return ResponseEntity.ok(ResponseService.createSuccessResponse("Subject updated successfully", updatedCount));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to update subject: " + e.getMessage()));
        }
    }
}