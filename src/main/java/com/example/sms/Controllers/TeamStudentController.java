package com.example.sms.Controllers;

import com.example.sms.Models.TeamStudent;
import com.example.sms.Services.ResponseService;
import com.example.sms.Services.TeamStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/team-students")
public class TeamStudentController {
    @Autowired
    private TeamStudentService teamStudentService;

    @GetMapping
    public Map<String, Object> getAllTeamStudents() {
        try {
            List<TeamStudent> teamStudents = teamStudentService.getAllTeamStudents();
            return ResponseService.createSuccessResponse("Team students retrieved successfully", teamStudents);
        } catch (Exception e) {
            return ResponseService.createErrorResponse(e.getMessage());
        }
    }

    @GetMapping("/team/{teamId}")
    public Map<String, Object> getTeamStudentsByTeam(@PathVariable Integer teamId) {
        try {
            List<TeamStudent> teamStudents = teamStudentService.getTeamStudentsByTeam(teamId);
            return ResponseService.createSuccessResponse("Team students retrieved successfully", teamStudents);
        } catch (Exception e) {
            return ResponseService.createErrorResponse(e.getMessage());
        }
    }

    @GetMapping("/student/{studentId}")
    public Map<String, Object> getTeamStudentsByStudent(@PathVariable Integer studentId) {
        try {
            List<TeamStudent> teamStudents = teamStudentService.getTeamStudentsByStudent(studentId);
            return ResponseService.createSuccessResponse("Team students retrieved successfully", teamStudents);
        } catch (Exception e) {
            return ResponseService.createErrorResponse(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Map<String, Object> getTeamStudentById(@PathVariable Integer id) {
        try {
            Optional<TeamStudent> teamStudent = teamStudentService.getTeamStudentById(id);
            return teamStudent.map(value -> ResponseService.createSuccessResponse("Team student retrieved successfully", value))
                    .orElseGet(() -> ResponseService.createErrorResponse("Team student not found"));
        } catch (Exception e) {
            return ResponseService.createErrorResponse(e.getMessage());
        }
    }

    @PostMapping
    public Map<String, Object> createTeamStudent(@RequestBody TeamStudent teamStudent) {
        try {
            TeamStudent newTeamStudent = teamStudentService.createTeamStudent(teamStudent);
            return ResponseService.createSuccessResponse("Team student created successfully", newTeamStudent);
        } catch (Exception e) {
            return ResponseService.createErrorResponse(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Map<String, Object> updateTeamStudent(@PathVariable Integer id, @RequestBody TeamStudent teamStudent) {
        try {
            TeamStudent updatedTeamStudent = teamStudentService.updateTeamStudent(id, teamStudent);
            return ResponseService.createSuccessResponse("Team student updated successfully", updatedTeamStudent);
        } catch (Exception e) {
            return ResponseService.createErrorResponse(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> deleteTeamStudent(@PathVariable Integer id) {
        try {
            teamStudentService.deleteTeamStudent(id);
            return ResponseService.createSuccessResponse("Team student deleted successfully", null);
        } catch (Exception e) {
            return ResponseService.createErrorResponse(e.getMessage());
        }
    }
}