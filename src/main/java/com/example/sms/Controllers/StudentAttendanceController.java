package com.example.sms.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sms.Models.Attendance;
import com.example.sms.Models.StudentAttendance;
import com.example.sms.Models.User;
import com.example.sms.Repositories.AttendanceRepository;
import com.example.sms.Repositories.UserRepository;
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
    private UserRepository userRepo;

    @GetMapping
    public List<StudentAttendance> getAllStudentsAttendances() {
        return service.getStudentAttendance();
    }

    @PostMapping
    public ResponseEntity<String> addStudentAttendances(@RequestBody List<Map<String, Object>> studentsJson) {
        List<StudentAttendance> attendances = new ArrayList<>();

        for (Map<String, Object> studentJson : studentsJson) {
            Integer attendanceId = (Integer) ((Map<String, Object>) studentJson.get("attendance")).get("attendanceId");
            Integer studentId = (Integer) ((Map<String, Object>) studentJson.get("student")).get("userId");
            String status = (String) studentJson.get("status");

            Attendance attendance = attendanceRepo.findById(attendanceId).orElse(null);
            User student = userRepo.findById(studentId).orElse(null);

            if (attendance == null || student == null) {
                return ResponseEntity.badRequest().body("Invalid attendanceId or studentId");
            }

            StudentAttendance sa = new StudentAttendance();
            sa.setAttendance(attendance);
            sa.setStudent(student);
            sa.setStatus(status);

            attendances.add(sa);
        }

        service.addStudentAttendances(attendances);
        return ResponseEntity.ok("Attendance records saved successfully");
    }

}
