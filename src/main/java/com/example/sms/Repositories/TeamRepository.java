package com.example.sms.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sms.Models.Team;

public interface TeamRepository extends JpaRepository<Team, Integer>{

    boolean existsByTeamName(String teamName);
    
}
