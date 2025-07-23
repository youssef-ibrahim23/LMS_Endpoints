package com.example.sms.Services;

import com.example.sms.Models.Team;
import com.example.sms.Repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Optional<Team> getTeamById(Integer id) {
        return teamRepository.findById(id);
    }

    public Team createTeam(Team team) {
        if (teamRepository.existsByTeamName(team.getTeamName())) {
            throw new RuntimeException("Team name already exists");
        }
        return teamRepository.save(team);
    }

    public Team updateTeam(Integer id, Team teamDetails) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found with id: " + id));

        if (!team.getTeamName().equals(teamDetails.getTeamName())) {
            if (teamRepository.existsByTeamName(teamDetails.getTeamName())) {
                throw new RuntimeException("Team name already exists");
            }
            team.setTeamName(teamDetails.getTeamName());
        }

        return teamRepository.save(team);
    }

}