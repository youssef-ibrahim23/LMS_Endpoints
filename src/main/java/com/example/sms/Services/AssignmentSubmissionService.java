package com.example.sms.Services;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sms.Models.Assignment;
import com.example.sms.Models.AssignmentSubmission;
import com.example.sms.Models.Student;
import com.example.sms.Repositories.AssignmentRepository;
import com.example.sms.Repositories.AssignmentSubmissionRepository;
import com.example.sms.Repositories.StudentRepository;

@Service
public class AssignmentSubmissionService {

    @Autowired
    private AssignmentSubmissionRepository repository;
    @Autowired
    private AssignmentRepository assignmentRepo;

    @Autowired
    private StudentRepository studentRepo;

    public AssignmentSubmission submitAssignment(AssignmentSubmission submission) {
        // Decode Base64 solution
        if (submission.getSolution() == null && submission.getSolution() != null) {
            byte[] decodedData = Base64.getDecoder().decode(submission.getSolution());
            submission.setSolution(decodedData);
        }

        // Get the real Assignment and Student from DB
        Integer assignmentId = submission.getAssignment().getAssignmentId();
        Integer studentId = submission.getStudent().getStudentId();

        Assignment fullAssignment = assignmentRepo.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found with id: " + assignmentId));

        Student fullStudent = studentRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

        submission.setAssignment(fullAssignment);
        submission.setStudent(fullStudent);

        return repository.save(submission);
    }

    public List<AssignmentSubmission> getAllSubmissions() {
        return repository.findAll();
    }

    public Optional<AssignmentSubmission> getSubmissionById(Integer id) {
        return repository.findById(id);
    }

    
}
