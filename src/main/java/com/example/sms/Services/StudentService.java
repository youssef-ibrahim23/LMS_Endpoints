package com.example.sms.Services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sms.Models.Student;
import com.example.sms.Repositories.StudentRepository;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public List<Map<String, Object>> countStudentsByGrade() {
        return studentRepository.countStudentsByGrade();
    }
    public List<Map<String, Object>> getTodaySessions(Integer student_id){
        return studentRepository.getTodaySessions(student_id);
    }
    public List<Map<String, Object>> getDegreeRate(Integer student_id){
        return studentRepository.getDegreeRate(student_id);
    }
    public Integer SubjectsNumber(Integer id){
        return studentRepository.SubjectsNumber(id);
    }
    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public Student addStudent(Student student){
        return studentRepository.save(student);
    }

    public Student updateStudent(Student student){
        return studentRepository.save(student);
    }
}
