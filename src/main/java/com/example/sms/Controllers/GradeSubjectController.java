package com.example.sms.Controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sms.Models.GradeSubject;
import com.example.sms.Services.GradeSubjectService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/grade-subjects")
public class GradeSubjectController {

    @Autowired
    private GradeSubjectService gradeSubjectService;

    // Create a new grade-subject
    @PostMapping
    public ResponseEntity<GradeSubject> createGradeSubject(@RequestBody GradeSubject gradeSubject) {
        return ResponseEntity.ok(gradeSubjectService.createGradeSubject(gradeSubject));
    }

    // Get a grade-subject by ID
    @GetMapping("/{id}")
    public ResponseEntity<GradeSubject> getGradeSubjectById(@PathVariable Integer id) {
        return gradeSubjectService.getGradeSubjectById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get all grade-subjects
    @GetMapping
    public ResponseEntity<List<GradeSubject>> getAllGradeSubjects() {
        return ResponseEntity.ok(gradeSubjectService.getAllGradeSubjects());
    }

    // Update a grade-subject by ID
    @PutMapping("/{id}")
    public ResponseEntity<GradeSubject> updateGradeSubject(@PathVariable Integer id, @RequestBody GradeSubject gradeSubject) {
        return ResponseEntity.ok(gradeSubjectService.updateGradeSubject(id, gradeSubject));
    }
    @GetMapping("/teachers_count")
    public List<Map<String, Long>> teachersCount(){
        return gradeSubjectService.getCountOfTeachers();
    }
}
