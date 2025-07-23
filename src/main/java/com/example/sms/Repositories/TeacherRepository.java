package com.example.sms.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sms.Models.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Integer>{
     boolean existsByUser_UserId(Integer userId);
    Optional<Teacher> findByUser_UserId(Integer userId);   
}
