package com.example.sms.Services;

import com.example.sms.Models.Student;
import com.example.sms.Models.Team;
import com.example.sms.Models.TeamStudent;
import com.example.sms.Repositories.StudentRepository;
import com.example.sms.Repositories.TeamRepository;
import com.example.sms.Repositories.TeamStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamStudentService {
    @Autowired
    private TeamStudentRepository teamStudentRepository;
    
    @Autowired
    private TeamRepository teamRepository;
    
    @Autowired
    private StudentRepository studentRepository;

    public List<TeamStudent> getAllTeamStudents() {
        return teamStudentRepository.findAll();
    }

    public List<TeamStudent> getTeamStudentsByTeam(Integer teamId) {
        return teamStudentRepository.findByTeam_TeamId(teamId);
    }

    public List<TeamStudent> getTeamStudentsByStudent(Integer studentId) {
        return teamStudentRepository.findByStudent_StudentId(studentId);
    }

    public Optional<TeamStudent> getTeamStudentById(Integer id) {
        return teamStudentRepository.findById(id);
    }

    public TeamStudent createTeamStudent(TeamStudent teamStudent) {
        // Validate team exists
        Team team = teamRepository.findById(teamStudent.getTeam().getTeamId())
                .orElseThrow(() -> new RuntimeException("Team not found"));
        
        // Validate student exists
        Student student = studentRepository.findById(teamStudent.getStudent().getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        // Check if student is already in the team
        if (teamStudentRepository.existsByTeam_TeamIdAndStudent_StudentId(
                team.getTeamId(), student.getStudentId())) {
            throw new RuntimeException("Student is already in this team");
        }
        
        teamStudent.setTeam(team);
        teamStudent.setStudent(student);
        
        return teamStudentRepository.save(teamStudent);
    }

    public TeamStudent updateTeamStudent(Integer id, TeamStudent teamStudentDetails) {
        TeamStudent teamStudent = teamStudentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TeamStudent not found with id: " + id));
        
        // Only role can be updated, team and student references cannot be changed
        teamStudent.setRole(teamStudentDetails.getRole());
        
        return teamStudentRepository.save(teamStudent);
    }

    public void deleteTeamStudent(Integer id) {
        TeamStudent teamStudent = teamStudentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TeamStudent not found with id: " + id));
        teamStudentRepository.delete(teamStudent);
    }
}