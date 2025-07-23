package com.example.sms.Models;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id")
    private Integer ratingId;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @Column(name = "rate", nullable = false)
    private Integer rate;

    @Column(name = "feedback")
    private String feedback;

    @Column(name = "rated_at", columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private Timestamp ratedAt;

    // Constructors
    public Rating() {
    }

    public Rating(Teacher teacher, Integer rate, String feedback, Timestamp ratedAt) {
        this.teacher = teacher;
        this.rate = rate;
        this.feedback = feedback;
        this.ratedAt = ratedAt;
    }

    // Getters and Setters
    public Integer getRatingId() {
        return ratingId;
    }

    public void setRatingId(Integer ratingId) {
        this.ratingId = ratingId;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Timestamp getRatedAt() {
        return ratedAt;
    }

    public void setRatedAt(Timestamp ratedAt) {
        this.ratedAt = ratedAt;
    }

    // Optional toString, equals, and hashCode if needed
}
