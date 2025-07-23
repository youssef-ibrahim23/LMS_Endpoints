package com.example.sms.Controllers;

import com.example.sms.Models.Team;
import com.example.sms.Services.ResponseService;
import com.example.sms.Services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/teams")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @GetMapping
    public Map<String, Object> getAllTeams() {
        try {
            List<Team> teams = teamService.getAllTeams();
            return ResponseService.createSuccessResponse("Teams retrieved successfully", teams);
        } catch (Exception e) {
            return ResponseService.createErrorResponse(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Map<String, Object> getTeamById(@PathVariable Integer id) {
        try {
            Optional<Team> team = teamService.getTeamById(id);
            return team.map(value -> ResponseService.createSuccessResponse("Team retrieved successfully", value))
                    .orElseGet(() -> ResponseService.createErrorResponse("Team not found"));
        } catch (Exception e) {
            return ResponseService.createErrorResponse(e.getMessage());
        }
    }

    @PostMapping
    public Map<String, Object> createTeam(@RequestBody Team team) {
        try {
            Team newTeam = teamService.createTeam(team);
            return ResponseService.createSuccessResponse("Team created successfully", newTeam);
        } catch (Exception e) {
            return ResponseService.createErrorResponse(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Map<String, Object> updateTeam(@PathVariable Integer id, @RequestBody Team team) {
        try {
            Team updatedTeam = teamService.updateTeam(id, team);
            return ResponseService.createSuccessResponse("Team updated successfully", updatedTeam);
        } catch (Exception e) {
            return ResponseService.createErrorResponse(e.getMessage());
        }
    }

}