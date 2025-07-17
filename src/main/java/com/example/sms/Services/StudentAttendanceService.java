package com.example.sms.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sms.Models.StudentAttendance;
import com.example.sms.Repositories.StudentAttendanceRepository;
import java.util.List;
@Service
public class StudentAttendanceService {
    @Autowired
    private  StudentAttendanceRepository studentAttendanceRepository;

    public List<StudentAttendance> getStudentAttendance(){
        return studentAttendanceRepository.findAll();
    }
    public List<StudentAttendance> addStudentAttendances(List<StudentAttendance> student){
        return studentAttendanceRepository.saveAll(student);
    }
}
