package com.example.sms.Controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.sms.Models.Attendance;
import com.example.sms.Services.AttendanceService;
import com.example.sms.Services.ResponseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/attendances")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/create")
    public ResponseEntity<?> createAttendance(@RequestBody Attendance attendance) {
        try {
            if (attendance == null) {
                return ResponseEntity.badRequest()
                        .body(ResponseService.createErrorResponse("Attendance data is required"));
            }
            if (attendance.getAttendDate() == null) {
                return ResponseEntity.badRequest()
                        .body(ResponseService.createErrorResponse("Date is required"));
            }

            Attendance createdAttendance = attendanceService.createAttendance(attendance);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ResponseService.createSuccessResponse("Attendance created successfully", createdAttendance));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseService.createErrorResponse("Failed to create attendance: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAttendanceById(@PathVariable Integer id) {
        try {
            // Validate input ID
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest()
                        .body(ResponseService.createErrorResponse("Invalid attendance ID"));
            }

            // Get attendance from service
            Optional<Attendance> attendance = attendanceService.getAttendanceById(id);

            // Return appropriate response
            if (attendance.isPresent()) {
                return ResponseEntity.ok(attendance.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseService.createErrorResponse("Attendance record not found with ID: " + id));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseService.createErrorResponse("Failed to retrieve attendance: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllAttendances() {
        try {
            List<Attendance> attendances = attendanceService.getAllAttendances();
            if (attendances.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(ResponseService.createSuccessResponse("No attendances found", null));
            }
            return ResponseEntity.ok(attendances);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseService.createErrorResponse("Failed to retrieve attendances: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAttendance(@PathVariable Integer id, @RequestBody Attendance attendance) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest()
                        .body(ResponseService.createErrorResponse("Invalid attendance ID"));
            }
            if (attendance == null) {
                return ResponseEntity.badRequest()
                        .body(ResponseService.createErrorResponse("Attendance data is required"));
            }

            Attendance updatedAttendance = attendanceService.updateAttendance(id, attendance);
            return ResponseEntity
                    .ok(ResponseService.createSuccessResponse("Attendance updated successfully", updatedAttendance));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseService.createErrorResponse("Failed to update attendance: " + e.getMessage()));
        }
    }

    @GetMapping("/status_counts")
    public ResponseEntity<?> getStatusCounts(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        try {
            if (from == null || to == null) {
                return ResponseEntity.badRequest()
                        .body(ResponseService.createErrorResponse("Both 'from' and 'to' dates are required"));
            }
            if (from.isAfter(to)) {
                return ResponseEntity.badRequest()
                        .body(ResponseService.createErrorResponse("'from' date cannot be after 'to' date"));
            }

            List<Map<String, Object>> statusCounts = attendanceService.countOfAbsentAndAttendees(
                    java.sql.Date.valueOf(from),
                    java.sql.Date.valueOf(to));

            if (statusCounts.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(ResponseService.createSuccessResponse("No attendance data found for the given period",
                                null));
            }

            return ResponseEntity
                    .ok(ResponseService.createSuccessResponse("Status counts retrieved successfully", statusCounts));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseService.createErrorResponse("Failed to retrieve status counts: " + e.getMessage()));
        }
    }

    @GetMapping("/percentages/{id}")
    List<Map<String, Object>> getAttendancePercentageByStudentId(@PathVariable Integer studentId) {

        return attendanceService.getAttendancePercentageByStudentId(studentId);
    }

    @GetMapping("/student-stats/{id}")
    public Map<String, Object> getAttendanceStatsByStudentId(@PathVariable Integer studentId) {

        return attendanceService.getAttendanceStatsByStudentId(studentId);

    }
}