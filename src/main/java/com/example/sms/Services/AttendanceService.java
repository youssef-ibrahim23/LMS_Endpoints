package com.example.sms.Services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.example.sms.Models.Assignment;
import com.example.sms.Models.Attendance;
import com.example.sms.Repositories.AttendanceRepository;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    // Create a new attendance record
    public Attendance createAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    // Retrieve a specific attendance record by its ID
    public Optional<Attendance> getAttendanceById(Integer id) {
        return attendanceRepository.findById(id);
    }

    // Retrieve all attendance records
    public List<Attendance> getAllAttendances() {
        return attendanceRepository.findAll();
    }

    // Update an existing attendance record
    public Attendance updateAttendance(Integer id, Attendance updatedAttendance) {
        return attendanceRepository.findById(id).map(attendance -> {
            attendance.setSession(updatedAttendance.getSession());
            attendance.setAttendDate(updatedAttendance.getAttendDate());
            attendance.setPresents(updatedAttendance.getPresents());
            attendance.setAbsences(updatedAttendance.getAbsences());
            return attendanceRepository.save(attendance);
        }).orElseThrow(() -> new RuntimeException("Attendance not found with id: " + id));
    }

public List<Map<String, Object>> countOfAbsentAndAttendees(java.sql.Date fromDate, java.sql.Date toDate) {
    return attendanceRepository.countOfAbsentAndAttendees(fromDate, toDate);
}

public List<Map<String, Object>> getAttendancePercentageByStudentId(Integer studentId){
    return attendanceRepository.getAttendancePercentageByStudentId(studentId);
}

public Map<String, Object> getAttendanceStatsByStudentId(Integer studentId){

    return attendanceRepository.getAttendanceStatsByStudentId(studentId);

}

}
