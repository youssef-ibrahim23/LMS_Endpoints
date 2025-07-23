package com.example.sms.Services;

import com.example.sms.Models.Rating;
import com.example.sms.Models.Teacher;
import com.example.sms.Repositories.RatingRepository;
import com.example.sms.Repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;
    
    @Autowired
    private TeacherRepository teacherRepository;

    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    public List<Rating> getRatingsByTeacher(Integer teacherId) {
        return ratingRepository.findByTeacher_TeacherId(teacherId);
    }

    public Rating createRating(Rating rating) {
        // Validate teacher exists
        Teacher teacher = teacherRepository.findById(rating.getTeacher().getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        
        // Validate rate is between 1 and 5
        if (rating.getRate() < 1 || rating.getRate() > 5) {
            throw new RuntimeException("Rating must be between 1 and 5");
        }
        
        // Set current timestamp if not provided
        if (rating.getRatedAt() == null) {
            rating.setRatedAt(Timestamp.from(Instant.now()));
        }
        
        rating.setTeacher(teacher);
        return ratingRepository.save(rating);
    }

    public Rating updateRating(Integer id, Rating ratingDetails) {
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rating not found with id: " + id));
        
        // Only rate and feedback can be updated
        if (ratingDetails.getRate() != null) {
            if (ratingDetails.getRate() < 1 || ratingDetails.getRate() > 5) {
                throw new RuntimeException("Rating must be between 1 and 5");
            }
            rating.setRate(ratingDetails.getRate());
        }
        
        if (ratingDetails.getFeedback() != null) {
            rating.setFeedback(ratingDetails.getFeedback());
        }
        
        return ratingRepository.save(rating);
    }
}