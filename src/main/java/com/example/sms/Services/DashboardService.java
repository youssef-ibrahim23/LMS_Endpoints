package com.example.sms.Services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    @Autowired
    private UserService userService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private ClassroomService classroomService;
    @Autowired
    private GradeService gradeService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ParentService parentService;
    @Autowired
    private GradeSubjectService gradeSubjectService;
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private StudentService studentService;

    public Map<String, Object> getDashboardData(LocalDate from, LocalDate to) {

        Map<String, Object> response = new HashMap<>();

        response.put("totalUsers", userService.getCountOfUsers());
        response.put("totalSubjects", subjectService.getCountOfSubjects());
        response.put("totalClasses", classroomService.getCountOfClasses());
        response.put("totalGrades", gradeService.getCountOfGrades());
        response.put("totalAccounts", accountService.getCountOfAccounts());

        response.put("totalStudents", userService.getCountOfStudents());
        response.put("totalTeachers", userService.getCountOfTeachers());
        response.put("totalParents", parentService.countOfParents());

        // Students by Grade
        Map<String, Integer> studentsByGrade = new HashMap<>();
        for (Map<String, Object> row : studentService.countStudentsByGrade()) {
            Object gradeObj = row.get("grade_name");
            Object countObj = row.get("student_count");
            if (gradeObj != null && countObj != null) {
                String grade = gradeObj.toString();
                Integer count = ((Number) countObj).intValue();
                studentsByGrade.put(grade, count);
            }
        }
        response.put("studentsByGrade", studentsByGrade);

        // Teachers by Subject
        Map<String, Integer> teachersBySubject = new HashMap<>();
        for (Map<String, Long> row : gradeSubjectService.getCountOfTeachers()) {
            Object subjectObj = row.get("subject_name");
            Object countObj = row.get("teacher_count");
            if (subjectObj != null && countObj != null) {
                String subject = subjectObj.toString();
                Long count = (Long) countObj;
                teachersBySubject.put(subject, count.intValue());
            }
        }
        response.put("teachersBySubject", teachersBySubject);

        List<Map<String, Object>> attendanceStats = attendanceService.countOfAbsentAndAttendees(
                Date.valueOf(from),
                Date.valueOf(to));

        response.put("attendanceByGrade", attendanceStats);

        return response;
    }
}
