package com.example.sms.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sms.Models.Classroom;
import com.example.sms.Repositories.ClassroomRepository;

@Service
public class ClassroomService {

    @Autowired
    private ClassroomRepository classroomRepository;

    // Create a new classroom
    public Classroom createClassroom(Classroom classroom) {
        return classroomRepository.save(classroom);
    }

    // Retrieve a classroom by its ID
    public Optional<Classroom> getClassroomById(Integer id) {
        return classroomRepository.findById(id);
    }

    // Retrieve all classrooms
    public List<Classroom> getAllClassrooms() {
        return classroomRepository.findAll();
    }

    // Update an existing classroom
    public Classroom updateClassroom(Integer id, Classroom updatedClassroom) {
        return classroomRepository.findById(id).map(classroom -> {
            classroom.setClassName(updatedClassroom.getClassName());
            return classroomRepository.save(classroom);
        }).orElseThrow(() -> new RuntimeException("Classroom not found with id: " + id));
    }

    // Delete a classroom by its ID
    public void deleteClassroom(Integer id) {
        if (!classroomRepository.existsById(id)) {
            throw new RuntimeException("Classroom not found with id: " + id);
        }
        classroomRepository.deleteById(id);
    }
    public Long getCountOfClasses(){
        return classroomRepository.count();
    }
}
