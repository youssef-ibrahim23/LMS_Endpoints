package com.example.sms.Services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sms.Models.GradeSubject;
import com.example.sms.Repositories.GradeSubjectRepository;

@Service
public class GradeSubjectService {

    @Autowired
    private GradeSubjectRepository gradeSubjectRepository;

    // Create a new grade-subject record
    public GradeSubject createGradeSubject(GradeSubject gradeSubject) {
        return gradeSubjectRepository.save(gradeSubject);
    }

    // Retrieve a grade-subject record by ID
    public Optional<GradeSubject> getGradeSubjectById(Integer id) {
        return gradeSubjectRepository.findById(id);
    }

    // Retrieve all grade-subject records
    public List<GradeSubject> getAllGradeSubjects() {
        return gradeSubjectRepository.findAll();
    }

    // Update an existing grade-subject record
    public GradeSubject updateGradeSubject(Integer id, GradeSubject updated) {
        return gradeSubjectRepository.findById(id).map(gs -> {
            gs.setGrade(updated.getGrade());
            gs.setSubject(updated.getSubject());
            gs.setTeacher(updated.getTeacher());
            return gradeSubjectRepository.save(gs);
        }).orElseThrow(() -> new RuntimeException("GradeSubject not found with id: " + id));
    }

    public List<Map<String, Long>> getCountOfTeachers(){
        return gradeSubjectRepository.getCountOfTeachersForEachSubject();
    }
}
