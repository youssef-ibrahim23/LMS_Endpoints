package com.example.sms.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sms.Models.Role;
import com.example.sms.Services.RoleService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/roles")

public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/{id}")
    public Optional<Role> getRoleById(@PathVariable Integer id) {
        return roleService.getRoleById(id);
    }
    @PostMapping
    public Role addRole(@RequestBody Role role){
        return roleService.addRole(role);
    }
    @PutMapping("/{id}")
    public Role editRole(@PathVariable Integer id, @RequestBody String name){
        return roleService.editRole(id, name);
    }
}
