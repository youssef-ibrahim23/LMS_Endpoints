package com.example.sms.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sms.DTO.LinkParentRequest;
import com.example.sms.Models.StudentParent;
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
    public ResponseEntity<String> addParentAndLink(@RequestBody LinkParentRequest request) {
        StudentParent saved = studentParentService.addParentAndLinkToStudent(request);
        return ResponseEntity.ok("Parent saved for: " + saved.getStudent());
    }
}
