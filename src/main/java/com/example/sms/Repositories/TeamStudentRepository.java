package com.example.sms.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sms.Models.TeamStudent;

public interface TeamStudentRepository extends JpaRepository<TeamStudent, Integer>{
     List<TeamStudent> findByTeam_TeamId(Integer teamId);
    List<TeamStudent> findByStudent_StudentId(Integer studentId);
    Optional<TeamStudent> findByTeam_TeamIdAndStudent_StudentId(Integer teamId, Integer studentId);
    boolean existsByTeam_TeamIdAndStudent_StudentId(Integer teamId, Integer studentId);   
}
