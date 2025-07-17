package com.example.sms.Models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name="student_attendances")
public class StudentAttendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="student_attendance_id")
    private Integer studentAttendanceId;

    @ManyToOne
    @JoinColumn(name="attendance_id")
    private Attendance attendance;

    @ManyToOne
    @JoinColumn(name="student_id")
    private User student;

    private String status;

    public Integer getStudentAttendanceId() {
        return studentAttendanceId;
    }

    public void setStudentAttendanceId(Integer id) {
        this.studentAttendanceId = id;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
