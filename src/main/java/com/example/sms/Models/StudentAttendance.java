package com.example.sms.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "student_attendances")
public class StudentAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_attendance_id")
    private Integer studentAttendanceId;

    @ManyToOne
    @JoinColumn(name = "attendance_id", nullable = false)
    private Attendance attendance;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "notes")
    private String notes;

    // Getters and Setters
    public Integer getStudentAttendanceId() {
        return studentAttendanceId;
    }

    public void setStudentAttendanceId(Integer studentAttendanceId) {
        this.studentAttendanceId = studentAttendanceId;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
