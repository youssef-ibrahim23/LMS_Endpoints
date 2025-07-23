package com.example.sms.Models;

import java.sql.Date;

import jakarta.persistence.*;

@Entity 
@Table(name = "assignment_submissions")
public class AssignmentSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "submission_id")
    private Integer submissionId;

    @ManyToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "solution")
    private byte[] solution;

    @Column(name = "note")
    private String note;

    @Column(name = "score")
    private Integer score;

    @Column(name = "feedback")
    private String feedback;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "submit_at")
    private Date submitAt;

    // Getters and Setters

    public Integer getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(Integer submissionId) {
        this.submissionId = submissionId;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public byte[] getSolution() {
        return solution;
    }

    public void setSolution(byte[] solution) {
        this.solution = solution;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getSubmitAt() {
        return submitAt;
    }

    public void setSubmitAt(Date submitAt) {
        this.submitAt = submitAt;
    }

    
}
