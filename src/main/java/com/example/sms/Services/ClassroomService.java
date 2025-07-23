package com.example.sms.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sms.Models.SchoolClass;
import com.example.sms.Repositories.ClassroomRepository;

@Service
public class ClassroomService {

    @Autowired
    private ClassroomRepository classroomRepository;

    // Create a new classroom
    public SchoolClass createClassroom(SchoolClass classroom) {
        return classroomRepository.save(classroom);
    }

    // Retrieve a classroom by its ID
    public Optional<SchoolClass> getSchoolClassById(Integer id) {
        return classroomRepository.findById(id);
    }

    // Retrieve all classrooms
    public List<SchoolClass> getAllSchoolClasss() {
        return classroomRepository.findAll();
    }

    // Update an existing classroom
    public SchoolClass updateSchoolClass(Integer id, SchoolClass updatedSchoolClass) {
        return classroomRepository.findById(id).map(classroom -> {
            classroom.setClassName(updatedSchoolClass.getClassName());
            return classroomRepository.save(classroom);
        }).orElseThrow(() -> new RuntimeException("SchoolClass not found with id: " + id));
    }

    public Long getCountOfClasses(){
        return classroomRepository.count();
    }
}
