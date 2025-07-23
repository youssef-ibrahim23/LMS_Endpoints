package com.example.sms.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sms.DTO.AttendanceSummaryDTO;
import com.example.sms.Models.Attendance;
import com.example.sms.Models.Student;
import com.example.sms.Models.StudentAttendance;
import com.example.sms.Models.User;
import com.example.sms.Repositories.AttendanceRepository;
import com.example.sms.Repositories.StudentRepository;
import com.example.sms.Repositories.UserRepository;
import com.example.sms.Services.ResponseService;
import com.example.sms.Services.StudentAttendanceService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/students_attendances")
public class StudentAttendanceController {

    @Autowired
    private StudentAttendanceService service;

    @Autowired
    private AttendanceRepository attendanceRepo;

    @Autowired
    private StudentRepository userRepo;

    @GetMapping
    public ResponseEntity<?> getAllStudentsAttendances() {
        try {
            List<StudentAttendance> attendances = service.getStudentAttendance();
            if (attendances.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(ResponseService.createSuccessResponse("No attendance records found", null));
            }
            return ResponseEntity.ok(attendances);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to retrieve attendance records: " + e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> addStudentAttendances(@RequestBody List<Map<String, Object>> studentsJson) {
        try {
            if (studentsJson == null || studentsJson.isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Attendance data is required"));
            }

            List<StudentAttendance> attendances = new ArrayList<>();
            List<String> validationErrors = new ArrayList<>();

            for (Map<String, Object> studentJson : studentsJson) {
                try {
                    if (studentJson.get("attendance") == null || studentJson.get("student") == null) {
                        validationErrors.add("Missing attendance or student data in one of the records");
                        continue;
                    }

                    Integer attendanceId = (Integer) ((Map<String, Object>) studentJson.get("attendance")).get("attendanceId");
                    Integer studentId = (Integer) ((Map<String, Object>) studentJson.get("student")).get("userId");
                    String status = (String) studentJson.get("status");

                    if (attendanceId == null || studentId == null || status == null) {
                        validationErrors.add("Missing required fields in one of the records");
                        continue;
                    }

                    Attendance attendance = attendanceRepo.findById(attendanceId)
                        .orElseThrow(() -> new IllegalArgumentException("Attendance not found with ID: " + attendanceId));
                    Student student = userRepo.findById(studentId)
                        .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));

                    StudentAttendance sa = new StudentAttendance();
                    sa.setAttendance(attendance);
                    sa.setStudent(student);
                    sa.setStatus(status);

                    attendances.add(sa);
                } catch (Exception e) {
                    validationErrors.add("Invalid data format in one of the records: " + e.getMessage());
                }
            }

            if (!validationErrors.isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Validation errors: " + String.join(", ", validationErrors)));
            }

            service.addStudentAttendances(attendances);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseService.createSuccessResponse("Attendance records saved successfully", attendances));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to save attendance records: " + e.getMessage()));
        }
    }

    @GetMapping("/attendance_summary/{student_id}")
    public ResponseEntity<?> attendanceSummary(@PathVariable Integer student_id) {
        try {
            if (student_id == null || student_id <= 0) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Invalid student ID"));
            }

            AttendanceSummaryDTO summary = service.attendanceSummary(student_id);
            if (summary == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseService.createErrorResponse("No attendance summary found for student ID: " + student_id));
            }
            return ResponseEntity.ok(summary);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to get attendance summary: " + e.getMessage()));
        }
    }
}