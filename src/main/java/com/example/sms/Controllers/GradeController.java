package com.example.sms.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sms.Models.Grade;
import com.example.sms.Services.GradeService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/grades")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    // Create a new grade
    @PostMapping
    public ResponseEntity<Grade> createGrade(@RequestBody Grade grade) {
        return ResponseEntity.ok(gradeService.createGrade(grade));
    }

    // Get a grade by ID
    @GetMapping("/{id}")
    public ResponseEntity<Grade> getGradeById(@PathVariable Integer id) {
        return gradeService.getGradeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get all grades
    @GetMapping
    public ResponseEntity<List<Grade>> getAllGrades() {
        return ResponseEntity.ok(gradeService.getAllGrades());
    }

    // Update a grade by ID
    @PutMapping("/{id}")
    public ResponseEntity<Grade> updateGrade(@PathVariable Integer id, @RequestBody Grade grade) {
        return ResponseEntity.ok(gradeService.updateGrade(id, grade));
    }
    @GetMapping("/count")
    public Long getCount(){
        return gradeService.getCountOfGrades();
    }
}
