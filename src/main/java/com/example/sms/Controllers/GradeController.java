package com.example.sms.Controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.sms.Models.Grade;
import com.example.sms.Services.GradeService;
import com.example.sms.Services.ResponseService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/grades")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @PostMapping
    public ResponseEntity<?> createGrade(@RequestBody Grade grade) {
        try {
            if (grade == null) {
                return ResponseEntity.badRequest()
                        .body(ResponseService.createErrorResponse("Grade data is required"));
            }
            if (grade.getGradeName() == null) {
                return ResponseEntity.badRequest()
                        .body(ResponseService.createErrorResponse("Grade name is required"));
            }

            Grade createdGrade = gradeService.createGrade(grade);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ResponseService.createSuccessResponse("Grade created successfully", createdGrade));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseService.createErrorResponse("Failed to create grade: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGradeById(@PathVariable Integer id) {
        try {
            // Validate input
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest()
                        .body(ResponseService.createErrorResponse("Invalid grade ID"));
            }

            // Get grade from service
            Optional<Grade> grade = gradeService.getGradeById(id);

            // Return appropriate response
            if (grade.isPresent()) {
                return ResponseEntity.ok(grade.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseService.createErrorResponse("Grade not found with ID: " + id));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseService.createErrorResponse("Failed to retrieve grade: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllGrades() {
        try {
            List<Grade> grades = gradeService.getAllGrades();
            if (grades.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(ResponseService.createSuccessResponse("No grades found", null));
            }
            return ResponseEntity.ok(grades);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseService.createErrorResponse("Failed to retrieve grades: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGrade(@PathVariable Integer id, @RequestBody Grade grade) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest()
                        .body(ResponseService.createErrorResponse("Invalid grade ID"));
            }
            if (grade == null) {
                return ResponseEntity.badRequest()
                        .body(ResponseService.createErrorResponse("Grade data is required"));
            }

            Grade updatedGrade = gradeService.updateGrade(id, grade);
            return ResponseEntity.ok(ResponseService.createSuccessResponse("Grade updated successfully", updatedGrade));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseService.createErrorResponse("Failed to update grade: " + e.getMessage()));
        }
    }

    @GetMapping("/count")
    public ResponseEntity<?> getCount() {
        try {
            Long count = gradeService.getCountOfGrades();
            return ResponseEntity
                    .ok(ResponseService.createSuccessResponse("Grade count retrieved successfully", count));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseService.createErrorResponse("Failed to get grade count: " + e.getMessage()));
        }
    }
}