package com.example.sms.Controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sms.DTO.UserDTO;
import com.example.sms.Models.User;
import com.example.sms.Services.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Integer id) {
        try {
            User user = userService.getUserByIdOrThrow(id);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/grade/{gradeId}")
    public ResponseEntity<List<User>> getUsersByGrade(@PathVariable Integer gradeId) {
        List<User> users = userService.getUsersByGrade(gradeId);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {
        userService.createUser(userDTO);
        return ResponseEntity.ok("User created successfully");
    }

    @GetMapping("/role/{roleID}")
    public Optional<List<User>> getUsersByRole(@PathVariable Integer roleID) {
        return userService.getUsersByRole(roleID);
    }

    @GetMapping("/count")
    public Long getCount() {
        return userService.getCountOfUsers();
    }

    @GetMapping("/student_count")
    public Long getStudentsCount() {
        return userService.getCountOfStudents();
    }

    @GetMapping("/teacher_count")
    public Long getTeachersCount() {
        return userService.getCountOfTeachers();
    }

    @GetMapping("/grades_counts")
    public List<Map<String, Long>> gradesCounts() {
        return userService.CountsOfGrades();
    }

    @PutMapping("/delete/{id}")
    public int delete(@PathVariable int id) {
        return userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Integer id, @RequestBody User updatedUser) {
        boolean success = userService.updateUser(id, updatedUser);
        if (success) {
            return ResponseEntity.ok("User updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }

}
