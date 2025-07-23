package com.example.sms.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sms.DTO.LinkParentRequest;
import com.example.sms.Models.Parent;
import com.example.sms.Models.Student;
import com.example.sms.Models.StudentParent;
import com.example.sms.Models.User;
import com.example.sms.Repositories.ParentRepository;
import com.example.sms.Repositories.StudentParentRepository;
import com.example.sms.Repositories.StudentRepository;
import com.example.sms.Repositories.UserRepository;

@Service
public class StudentParentService {
    private final StudentParentRepository studentParentRepository;
    private final StudentRepository studentRepository;
    private final ParentService parentService;

    @Autowired
    public StudentParentService(ParentRepository parentRepository,
            StudentRepository studentRepository,
            StudentParentRepository studentParentRepository,
            ParentService parentService) {
        this.studentRepository = studentRepository;
        this.studentParentRepository = studentParentRepository;
        this.parentService = parentService;
    }

    public StudentParent addParentAndLinkToStudent(LinkParentRequest request) {
        // Create and save parent
        Parent parent = new Parent();
        parent.setName(request.getName());
        parent.setEmail(request.getEmail());
        parent.setPhoneNumber(request.getPhoneNumber());
        parent.setAddress(request.getAddress());
        parent.setGender(request.getGender());

        Parent savedParent = parentService.addParent(parent);

        // Get student by ID
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Create and save the relationship
        StudentParent link = new StudentParent();
        link.setParent(savedParent);
        link.setStudent(student);

        return studentParentRepository.save(link);
    }
}
