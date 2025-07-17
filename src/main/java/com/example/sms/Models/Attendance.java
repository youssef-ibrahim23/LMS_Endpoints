package com.example.sms.Models;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name="attendances")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer attendanceId;

    @ManyToOne
    @JoinColumn(name="session_id")
    private Session session;

    private Date attendDate;
    private Integer attendees;
    private Integer absence;
    public Integer getAttendanceId() {
        return attendanceId;
    }
    public void setAttendanceId(Integer attendanceId) {
        this.attendanceId = attendanceId;
    }
    public Session getSession() {
        return session;
    }
    public void setSession(Session session) {
        this.session = session;
    }
    public Date getAttendDate() {
        return attendDate;
    }
    public void setAttendDate(Date attendDate) {
        this.attendDate = attendDate;
    }
    public Integer getAttendees() {
        return attendees;
    }
    public void setAttendees(Integer attendees) {
        this.attendees = attendees;
    }
    public Integer getAbsence() {
        return absence;
    }
    public void setAbsence(Integer absence) {
        this.absence = absence;
    }
    
}
