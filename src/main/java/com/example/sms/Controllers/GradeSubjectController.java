package com.example.sms.Controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import com.example.sms.Models.GradeSubject;
import com.example.sms.Services.GradeSubjectService;
import com.example.sms.Services.ResponseService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/grade-subjects")
public class GradeSubjectController {

    @Autowired
    private GradeSubjectService gradeSubjectService;

    @PostMapping
    public ResponseEntity<?> createGradeSubject(@RequestBody GradeSubject gradeSubject) {
        try {
            if (gradeSubject == null) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Grade-subject data is required"));
            }
            if (gradeSubject.getGradeSubjectId() == null || gradeSubject.getGradeSubjectId() <= 0) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Valid subject ID is required"));
            }

            GradeSubject createdGradeSubject = gradeSubjectService.createGradeSubject(gradeSubject);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseService.createSuccessResponse("Grade-subject created successfully", createdGradeSubject));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to create grade-subject: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGradeSubjectById(@PathVariable Integer id) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Invalid grade-subject ID"));
            }

            Optional<GradeSubject> gradeSubject = gradeSubjectService.getGradeSubjectById(id);
            if (gradeSubject.isPresent()) {
                return ResponseEntity.ok(gradeSubject.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseService.createErrorResponse("Grade-subject not found with ID: " + id));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to retrieve grade-subject: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllGradeSubjects() {
        try {
            List<GradeSubject> gradeSubjects = gradeSubjectService.getAllGradeSubjects();
            if (gradeSubjects.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(ResponseService.createSuccessResponse("No grade-subjects found", null));
            }
            return ResponseEntity.ok(gradeSubjects);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to retrieve grade-subjects: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGradeSubject(@PathVariable Integer id, @RequestBody GradeSubject gradeSubject) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Invalid grade-subject ID"));
            }
            if (gradeSubject == null) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Grade-subject data is required"));
            }

            GradeSubject updatedGradeSubject = gradeSubjectService.updateGradeSubject(id, gradeSubject);
            return ResponseEntity.ok(ResponseService.createSuccessResponse("Grade-subject updated successfully", updatedGradeSubject));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to update grade-subject: " + e.getMessage()));
        }
    }

    @GetMapping("/teachers_count")
    public ResponseEntity<?> teachersCount() {
        try {
            List<Map<String, Long>> teachersCount = gradeSubjectService.getCountOfTeachers();
            if (teachersCount.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(ResponseService.createSuccessResponse("No teachers count data available", null));
            }
            return ResponseEntity.ok(teachersCount);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to get teachers count: " + e.getMessage()));
        }
    }
}