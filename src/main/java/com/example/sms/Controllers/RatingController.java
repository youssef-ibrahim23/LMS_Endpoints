package com.example.sms.Controllers;

import com.example.sms.Models.Rating;
import com.example.sms.Services.RatingService;
import com.example.sms.Services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/ratings")
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @GetMapping
    public Map<String, Object> getAllRatings() {
        try {
            List<Rating> ratings = ratingService.getAllRatings();
            return ResponseService.createSuccessResponse("Ratings retrieved successfully", ratings);
        } catch (Exception e) {
            return ResponseService.createErrorResponse(e.getMessage());
        }
    }

    @GetMapping("/{teacherId}")
    public Map<String, Object> getRatingsByTeacher(@PathVariable Integer teacherId) {
        try {
            List<Rating> ratings = ratingService.getRatingsByTeacher(teacherId);
            return ResponseService.createSuccessResponse("Ratings retrieved successfully", ratings);
        } catch (Exception e) {
            return ResponseService.createErrorResponse(e.getMessage());
        }
    }

    @PostMapping
    public Map<String, Object> createRating(@RequestBody Rating rating) {
        try {
            Rating newRating = ratingService.createRating(rating);
            return ResponseService.createSuccessResponse("Rating created successfully", newRating);
        } catch (Exception e) {
            return ResponseService.createErrorResponse(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Map<String, Object> updateRating(@PathVariable Integer id, @RequestBody Rating rating) {
        try {
            Rating updatedRating = ratingService.updateRating(id, rating);
            return ResponseService.createSuccessResponse("Rating updated successfully", updatedRating);
        } catch (Exception e) {
            return ResponseService.createErrorResponse(e.getMessage());
        }
    }
}