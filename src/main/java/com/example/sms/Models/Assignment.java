package com.example.sms.Models;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private Integer assignmentId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "subtitle")
    private String subtitle;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "grade_subject_id", nullable = false)
    private GradeSubject gradeSubject;

    @Column(name = "deadline", nullable = false)
    private Timestamp deadline;

    @Column(name = "status", columnDefinition = "varchar(15) default 'Urgent'")
    private String status = "Urgent";

    @Column(name = "max_score")
    private Integer maxScore;

    // Getters and Setters

    public Integer getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GradeSubject getGradeSubject() {
        return gradeSubject;
    }

    public void setGradeSubject(GradeSubject gradeSubject) {
        this.gradeSubject = gradeSubject;
    }

    public Timestamp getDeadline() {
        return deadline;
    }

    public void setDeadline(Timestamp deadline) {
        this.deadline = deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }
}
