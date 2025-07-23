package com.example.sms.Models;

import jakarta.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "exams")
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_id")
    private Integer examId;

    @ManyToOne
    @JoinColumn(name = "grade_subject_id", nullable = false)
    private GradeSubject gradeSubject;

    @Column(name = "exam_name", nullable = false)
    private String examName;

    @Column(name = "exam_date", nullable = false)
    private Date examDate;

    @Column(name = "start_time", nullable = false)
    private Time startTime;

    @Column(name = "end_time", nullable = false)
    private Time endTime;

    @Column(name = "max_degree", nullable = false)
    private Integer maxDegree;

    @Column(name = "success_degree", nullable = false)
    private Integer successDegree;

    @Column(name = "type", nullable = false)
    private String type;

    // Getters and Setters
    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public GradeSubject getGradeSubject() {
        return gradeSubject;
    }

    public void setGradeSubject(GradeSubject gradeSubject) {
        this.gradeSubject = gradeSubject;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Integer getMaxDegree() {
        return maxDegree;
    }

    public void setMaxDegree(Integer maxDegree) {
        this.maxDegree = maxDegree;
    }

    public Integer getSuccessDegree() {
        return successDegree;
    }

    public void setSuccessDegree(Integer successDegree) {
        this.successDegree = successDegree;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
