package com.example.sms.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sms.Models.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}