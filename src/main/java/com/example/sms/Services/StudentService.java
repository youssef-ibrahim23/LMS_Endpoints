package com.example.sms.Services;

import org.springframework.stereotype.Service;

import com.example.sms.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
@Service
public class StudentService {
    @Autowired
    private  StudentRepository studentRepository;

    public List<Map<String, Object>> countStudentsByGrade() {
    return studentRepository.countStudentsByGrade();
}
}
