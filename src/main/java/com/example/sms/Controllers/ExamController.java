package com.example.sms.Controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.sms.Models.Exam;
import com.example.sms.Services.ExamService;
import com.example.sms.Services.ResponseService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/exams")
public class ExamController {

    @Autowired
    private ExamService examService;

    @PostMapping
    public ResponseEntity<?> createExam(@RequestBody Exam exam) {
        try {
            if (exam == null) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Exam data is required"));
            }
            if (exam.getType() == null || exam.getType().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Exam type is required"));
            }
            if (exam.getExamDate() == null) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Exam date is required"));
            }

            Exam createdExam = examService.createExam(exam);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseService.createSuccessResponse("Exam created successfully", createdExam));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to create exam: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getExamById(@PathVariable Integer id) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Invalid exam ID"));
            }

            Optional<Exam> exam = examService.getExamById(id);
            if (exam.isPresent()) {
                return ResponseEntity.ok(exam.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseService.createErrorResponse("Exam not found with ID: " + id));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to retrieve exam: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllExams() {
        try {
            List<Exam> exams = examService.getAllExams();
            if (exams.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(ResponseService.createSuccessResponse("No exams found", null));
            }
            return ResponseEntity.ok(exams);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to retrieve exams: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateExam(@PathVariable Integer id, @RequestBody Exam exam) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Invalid exam ID"));
            }
            if (exam == null) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Exam data is required"));
            }

            Exam updatedExam = examService.updateExam(id, exam);
            return ResponseEntity.ok(ResponseService.createSuccessResponse("Exam updated successfully", updatedExam));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to update exam: " + e.getMessage()));
        }
    }
}