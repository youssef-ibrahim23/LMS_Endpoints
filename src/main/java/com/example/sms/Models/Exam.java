package com.example.sms.Models;

import java.sql.Date;
import java.sql.Time;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name="exams")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer examId;

    @ManyToOne
    @JoinColumn(name="grade_subject_id")
    private GradeSubject gradeSubject;

    private Date examDate;
    private Time startTime;
    private Time endTime;
    private Integer maxDegree;
    private Integer successDegree;
    private String type;
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
