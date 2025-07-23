package com.example.sms.Controllers;

import com.example.sms.Models.Teacher;
import com.example.sms.Services.ResponseService;
import com.example.sms.Services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/teachers")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @GetMapping
    public Map<String, Object> getAllTeachers() {
        try {
            List<Teacher> teachers = teacherService.getAllTeachers();
            return ResponseService.createSuccessResponse("Teachers retrieved successfully", teachers);
        } catch (Exception e) {
            return ResponseService.createErrorResponse(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Map<String, Object> getTeacherById(@PathVariable Integer id) {
        try {
            Optional<Teacher> teacher = teacherService.getTeacherById(id);
            return teacher.map(value -> ResponseService.createSuccessResponse("Teacher retrieved successfully", value))
                    .orElseGet(() -> ResponseService.createErrorResponse("Teacher not found"));
        } catch (Exception e) {
            return ResponseService.createErrorResponse(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public Map<String, Object> getTeacherByUserId(@PathVariable Integer userId) {
        try {
            Optional<Teacher> teacher = teacherService.getTeacherByUserId(userId);
            return teacher.map(value -> ResponseService.createSuccessResponse("Teacher retrieved successfully", value))
                    .orElseGet(() -> ResponseService.createErrorResponse("Teacher not found for this user"));
        } catch (Exception e) {
            return ResponseService.createErrorResponse(e.getMessage());
        }
    }

    @PostMapping
    public Map<String, Object> createTeacher(@RequestBody Teacher teacher) {
        try {
            Teacher newTeacher = teacherService.createTeacher(teacher);
            return ResponseService.createSuccessResponse("Teacher created successfully", newTeacher);
        } catch (Exception e) {
            return ResponseService.createErrorResponse(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Map<String, Object> updateTeacher(@PathVariable Integer id, @RequestBody Teacher teacher) {
        try {
            Teacher updatedTeacher = teacherService.updateTeacher(id, teacher);
            return ResponseService.createSuccessResponse("Teacher updated successfully", updatedTeacher);
        } catch (Exception e) {
            return ResponseService.createErrorResponse(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> deleteTeacher(@PathVariable Integer id) {
        try {
            teacherService.deleteTeacher(id);
            return ResponseService.createSuccessResponse("Teacher deleted successfully", null);
        } catch (Exception e) {
            return ResponseService.createErrorResponse(e.getMessage());
        }
    }
}