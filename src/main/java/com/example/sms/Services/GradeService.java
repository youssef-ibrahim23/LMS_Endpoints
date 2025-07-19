package com.example.sms.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sms.Models.Grade;
import com.example.sms.Repositories.GradeRepository;

@Service
public class GradeService {

    @Autowired
    private GradeRepository gradeRepository;

    // Create a new grade
    public Grade createGrade(Grade grade) {
        return gradeRepository.save(grade);
    }

    // Retrieve a grade by its ID
    public Optional<Grade> getGradeById(Integer id) {
        return gradeRepository.findById(id);
    }

    // Retrieve all grades
    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }

    // Update an existing grade
    public Grade updateGrade(Integer id, Grade updatedGrade) {
        return gradeRepository.findById(id).map(grade -> {
            grade.setGradeName(updatedGrade.getGradeName());
            return gradeRepository.save(grade);
        }).orElseThrow(() -> new RuntimeException("Grade not found with id: " + id));
    }
    public Long getCountOfGrades(){
        return gradeRepository.count();
    }
}
