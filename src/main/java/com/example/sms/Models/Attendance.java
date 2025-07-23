package com.example.sms.Models;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "attendances")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id")
    private Integer attendanceId;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @Column(name = "attend_date", columnDefinition = "date default CURRENT_DATE")
    private Date attendDate;

    @Column(name = "presents")
    private Integer presents;
    
    @Column(name = "absences")
    private Integer absences;

    // Getters and Setters
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

    public Integer getPresents() {
        return presents;
    }

    public void setPresents(Integer presents) {
        this.presents = presents;
    }

    public Integer getAbsences() {
        return absences;
    }

    public void setAbsences(Integer absences) {
        this.absences = absences;
    }

    
}
