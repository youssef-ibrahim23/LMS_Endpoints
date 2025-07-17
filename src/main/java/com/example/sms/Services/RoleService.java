package com.example.sms.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sms.Models.Role;
import com.example.sms.Repositories.RoleRepository;
import java.util.List;
import java.util.Optional;
@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }
    public Optional<Role> getRoleById(Integer id){
        return roleRepository.findById(id);
    }
    public Role addRole(Role role){
       return roleRepository.save(role);
    }
}
