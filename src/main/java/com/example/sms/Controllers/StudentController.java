package com.example.sms.Controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sms.Models.Student;
import com.example.sms.Services.StudentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/students")
@CrossOrigin("*")
public class StudentController {
    @Autowired
    private StudentService service;

    @Autowired
    private StudentService studentService;

    @GetMapping("/today_sessions/{student_id}")
    public List<Map<String, Object>> getTodaySessions(@PathVariable Integer student_id) {
        return service.getTodaySessions(student_id);
    }

    @GetMapping("/count_by_grade")
    public List<Map<String, Object>> countStudentsByGrade() {
        return service.countStudentsByGrade();
    }

    @GetMapping("/degree_rate/{student_id}")
    public List<Map<String, Object>> getDegreeRage(@PathVariable Integer student_id) {
        return service.getDegreeRate(student_id);
    }

    @GetMapping("/subjects_number/{id}")
    public Integer SubjectsNumber(@PathVariable Integer id) {
        return service.SubjectsNumber(id);
    }

// Get all students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    // Add a new student
    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student createdStudent = studentService.addStudent(student);
        return ResponseEntity.ok(createdStudent);
    }

    // Update existing student
    @PutMapping("/edit/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Integer id, @RequestBody Student student) {
        student.getUser().setUserId(id); // Ensure the correct ID is set
        Student updatedStudent = studentService.updateStudent(student);
        return ResponseEntity.ok(updatedStudent);
    }

}
