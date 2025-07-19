package com.example.sms.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.sms.Models.AssignmentSubmission;
import com.example.sms.Services.AssignmentSubmissionService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/v1/assignment-submissions")
@CrossOrigin(origins = "*")
public class AssignmentSubmissionController {
    @Autowired
    private AssignmentSubmissionService service;

    @GetMapping
    public List<AssignmentSubmission> getAll() throws SQLException {
        return service.getAllSubmissions();
    }

    @GetMapping("/{id}")
    public Optional<AssignmentSubmission> getById(@PathVariable Integer id) {
        return service.getSubmissionById(id);
    }

    @PostMapping
    public ResponseEntity<String> submitAssignment(@RequestBody AssignmentSubmission submission) {
        service.submitAssignment(submission);
        return ResponseEntity.ok("Your submission is sent successfully");
    }

}
