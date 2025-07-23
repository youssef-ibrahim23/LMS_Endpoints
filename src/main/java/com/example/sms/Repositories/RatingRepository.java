package com.example.sms.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sms.Models.Rating;
import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
    List<Rating> findByTeacher_TeacherId(Integer teacherId);
    boolean existsByTeacher_TeacherId(Integer teacherId);
}