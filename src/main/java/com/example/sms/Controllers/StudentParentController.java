package com.example.sms.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sms.DTO.LinkParentRequest;
import com.example.sms.Models.StudentParent;
import com.example.sms.Services.ResponseService;
import com.example.sms.Services.StudentParentService;

@RestController
@RequestMapping("/api/v1/students_parent")
@CrossOrigin(origins = "*")
public class StudentParentController {
    private final StudentParentService studentParentService;

    @Autowired
    public StudentParentController(StudentParentService studentParentService) {
        this.studentParentService = studentParentService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addParentAndLink(@RequestBody LinkParentRequest request) {
        try {
            if (request == null) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Request body cannot be null"));
            }

            // Validate required fields
            if (request.getStudentId() == null) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("StudentIdis  required"));
            }

            StudentParent saved = studentParentService.addParentAndLinkToStudent(request);
            
            if (saved == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseService.createErrorResponse("Failed to save parent-student relationship"));
            }

            return ResponseEntity.ok(
                ResponseService.createSuccessResponse("Parent saved and linked successfully", saved)
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to process parent-student linking: " + e.getMessage()));
        }
    }
}