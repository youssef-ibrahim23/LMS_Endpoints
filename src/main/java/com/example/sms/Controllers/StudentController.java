package com.example.sms.Controllers;

import java.util.List;
import java.util.Map;

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

import com.example.sms.Models.Student;
import com.example.sms.Services.ResponseService;
import com.example.sms.Services.StudentService;

@RestController
@RequestMapping("/api/v1/students")
@CrossOrigin("*")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/today_sessions/{student_id}")
    public ResponseEntity<?> getTodaySessions(@PathVariable Integer student_id) {
        try {
            if (student_id == null || student_id <= 0) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Invalid student ID"));
            }

            List<Map<String, Object>> sessions = studentService.getTodaySessions(student_id);
            if (sessions.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(ResponseService.createSuccessResponse("No sessions found for today", null));
            }
            return ResponseEntity.ok(sessions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to get today's sessions: " + e.getMessage()));
        }
    }

    @GetMapping("/count_by_grade")
    public ResponseEntity<?> countStudentsByGrade() {
        try {
            List<Map<String, Object>> counts = studentService.countStudentsByGrade();
            if (counts.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(ResponseService.createSuccessResponse("No student counts available", null));
            }
            return ResponseEntity.ok(counts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to get student counts by grade: " + e.getMessage()));
        }
    }

    @GetMapping("/degree_rate/{student_id}")
    public ResponseEntity<?> getDegreeRate(@PathVariable Integer student_id) {
        try {
            if (student_id == null || student_id <= 0) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Invalid student ID"));
            }

            List<Map<String, Object>> degreeRate = studentService.getDegreeRate(student_id);
            if (degreeRate.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseService.createErrorResponse("No degree rate data found for student"));
            }
            return ResponseEntity.ok(degreeRate);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to get degree rate: " + e.getMessage()));
        }
    }

    @GetMapping("/subjects_number/{id}")
    public ResponseEntity<?> getSubjectsNumber(@PathVariable Integer id) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Invalid student ID"));
            }

            Integer count = studentService.SubjectsNumber(id);
            return ResponseEntity.ok(ResponseService.createSuccessResponse("Subjects count retrieved", count));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to get subjects count: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllStudents() {
        try {
            List<Student> students = studentService.getAllStudents();
            if (students.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(ResponseService.createSuccessResponse("No students found", null));
            }
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to retrieve students: " + e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> addStudent(@RequestBody Student student) {
        try {
            if (student == null) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Student data is required"));
            }
            if (student.getUser() == null || student.getUser().getFirstName() == null || 
                student.getUser().getFirstName().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("First name is required"));
            }

            Student createdStudent = studentService.addStudent(student);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseService.createSuccessResponse("Student created successfully", createdStudent));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to create student: " + e.getMessage()));
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Integer id, @RequestBody Student student) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Invalid student ID"));
            }
            if (student == null || student.getUser() == null) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Student data is required"));
            }

            student.getUser().setUserId(id); // Ensure the correct ID is set
            Student updatedStudent = studentService.updateStudent(student);
            return ResponseEntity.ok(ResponseService.createSuccessResponse("Student updated successfully", updatedStudent));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to update student: " + e.getMessage()));
        }
    }
}