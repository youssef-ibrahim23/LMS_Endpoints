package com.example.sms.Services;

import com.example.sms.Models.Teacher;
import com.example.sms.Models.User;
import com.example.sms.Repositories.TeacherRepository;
import com.example.sms.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    
    @Autowired
    private UserRepository userRepository;

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public Optional<Teacher> getTeacherById(Integer id) {
        return teacherRepository.findById(id);
    }

    public Optional<Teacher> getTeacherByUserId(Integer userId) {
        return teacherRepository.findByUser_UserId(userId);
    }

    public Teacher createTeacher(Teacher teacher) {
        if (teacherRepository.existsByUser_UserId(teacher.getUser().getUserId())) {
            throw new RuntimeException("User is already assigned as a teacher");
        }
        return teacherRepository.save(teacher);
    }

    public Teacher updateTeacher(Integer id, Teacher teacherDetails) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));

        // Update basic fields
        teacher.setSpecialization(teacherDetails.getSpecialization());
        teacher.setYearsOfExperience(teacherDetails.getYearsOfExperience());
        teacher.setQualification(teacherDetails.getQualification());

        // Update user reference if changed
        if (!teacher.getUser().getUserId().equals(teacherDetails.getUser().getUserId())) {
            if (teacherRepository.existsByUser_UserId(teacherDetails.getUser().getUserId())) {
                throw new RuntimeException("User is already assigned as another teacher");
            }
            teacher.setUser(teacherDetails.getUser());
        }

        return teacherRepository.save(teacher);
    }

    public void deleteTeacher(Integer id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));
        teacherRepository.delete(teacher);
    }
}