package com.example.sms.Models;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Integer sessionId;

    @ManyToOne
    @JoinColumn(name = "grade_subject_id", nullable = false)
    private GradeSubject gradeSubject;

    @Column(name = "session_date", nullable = false)
    private Date sessionDate;

    @Column(name = "session_number", nullable = false)
    private Integer sessionNumber;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "status", nullable = false, columnDefinition = "varchar(10) default 'Coming'")
    private String status = "Coming";

    // Constructors
    public Session() {
    }

    public Session(GradeSubject gradeSubject, Date sessionDate, Integer sessionNumber, String type, String status) {
        this.gradeSubject = gradeSubject;
        this.sessionDate = sessionDate;
        this.sessionNumber = sessionNumber;
        this.type = type;
        this.status = status;
    }

    public Session(Integer sessionId, GradeSubject gradeSubject, Date sessionDate, Integer sessionNumber, String type, String status) {
        this.sessionId = sessionId;
        this.gradeSubject = gradeSubject;
        this.sessionDate = sessionDate;
        this.sessionNumber = sessionNumber;
        this.type = type;
        this.status = status;
    }

    // Getters and Setters
    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public GradeSubject getGradeSubject() {
        return gradeSubject;
    }

    public void setGradeSubject(GradeSubject gradeSubject) {
        this.gradeSubject = gradeSubject;
    }

    public Date getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(Date sessionDate) {
        this.sessionDate = sessionDate;
    }

    public Integer getSessionNumber() {
        return sessionNumber;
    }

    public void setSessionNumber(Integer sessionNumber) {
        this.sessionNumber = sessionNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // toString
    @Override
    public String toString() {
        return "Session{" +
                "sessionId=" + sessionId +
                ", gradeSubject=" + (gradeSubject != null ? gradeSubject.getGradeSubjectId() : "null") +
                ", sessionDate=" + sessionDate +
                ", sessionNumber=" + sessionNumber +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
