package com.example.sms.Controllers;

import java.util.List;
import java.util.Map;

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
import com.example.sms.Services.ResponseService;
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
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            if (users.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(ResponseService.createSuccessResponse("No users found", null));
            }
            return ResponseEntity.ok(ResponseService.createSuccessResponse("Users retrieved successfully", users));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to retrieve users: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Integer id) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Invalid user ID"));
            }
            
            User user = userService.getUserByIdOrThrow(id);
            return ResponseEntity.ok(ResponseService.createSuccessResponse("User retrieved successfully", user));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to retrieve user: " + e.getMessage()));
        }
    }

    @GetMapping("/grade/{gradeId}")
    public ResponseEntity<?> getUsersByGrade(@PathVariable Integer gradeId) {
        try {
            if (gradeId == null || gradeId <= 0) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Invalid grade ID"));
            }
            
            List<User> users = userService.getUsersByGrade(gradeId);
            if (users.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(ResponseService.createSuccessResponse("No users found for grade ID: " + gradeId, null));
            }
            return ResponseEntity.ok(ResponseService.createSuccessResponse("Users retrieved successfully", users));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to retrieve users by grade: " + e.getMessage()));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        try {
            if (userDTO == null) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("User data cannot be null"));
            }
            
            // Validate required fields
            if (userDTO.getFirstName() == null || userDTO.getFirstName().trim().isEmpty() ||
                userDTO.getLastName() == null || userDTO.getLastName().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("First name and last name are required"));
            }
            
            User createdUser = userService.createUser(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseService.createSuccessResponse("User created successfully", createdUser));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to create user: " + e.getMessage()));
        }
    }

    @GetMapping("/role/{roleID}")
    public ResponseEntity<?> getUsersByRole(@PathVariable Integer roleID) {
        try {
            if (roleID == null || roleID <= 0) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Invalid role ID"));
            }
            
            List<User> users = userService.getUsersByRoleId(roleID);
            if (users.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(ResponseService.createSuccessResponse("No users found for role ID: " + roleID, null));
            }
            return ResponseEntity.ok(ResponseService.createSuccessResponse("Users retrieved successfully", users));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to retrieve users by role: " + e.getMessage()));
        }
    }

    @GetMapping("/count")
    public ResponseEntity<?> getCount() {
        try {
            Long count = userService.getCountOfUsers();
            return ResponseEntity.ok(ResponseService.createSuccessResponse("User count retrieved successfully", count));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to get user count: " + e.getMessage()));
        }
    }

    @GetMapping("/student_count")
    public ResponseEntity<?> getStudentsCount() {
        try {
            Long count = userService.getCountOfStudents();
            return ResponseEntity.ok(ResponseService.createSuccessResponse("Student count retrieved successfully", count));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to get student count: " + e.getMessage()));
        }
    }

    @GetMapping("/teacher_count")
    public ResponseEntity<?> getTeachersCount() {
        try {
            Long count = userService.getCountOfTeachers();
            return ResponseEntity.ok(ResponseService.createSuccessResponse("Teacher count retrieved successfully", count));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to get teacher count: " + e.getMessage()));
        }
    }

    @GetMapping("/grades_counts")
    public ResponseEntity<?> gradesCounts() {
        try {
            List<Map<String, Long>> counts = userService.countOfGrades();
            if (counts.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(ResponseService.createSuccessResponse("No grade counts available", null));
            }
            return ResponseEntity.ok(ResponseService.createSuccessResponse("Grade counts retrieved successfully", counts));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to get grade counts: " + e.getMessage()));
        }
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            if (id <= 0) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Invalid user ID"));
            }
            
            int result = userService.deleteUser(id);
            if (result == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseService.createErrorResponse("User not found with ID: " + id));
            }
            return ResponseEntity.ok(ResponseService.createSuccessResponse("User deleted successfully", result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to delete user: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody User updatedUser) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Invalid user ID"));
            }
            
            if (updatedUser == null) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("User data cannot be null"));
            }
            
            boolean success = userService.updateUser(id, updatedUser);
            if (!success) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseService.createErrorResponse("User not found with ID: " + id));
            }
            return ResponseEntity.ok(ResponseService.createSuccessResponse("User updated successfully", null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to update user: " + e.getMessage()));
        }
    }

    @GetMapping("/no-accounts")
    public ResponseEntity<?> getUsersWithoutAccounts() {
        try {
            List<User> users = userService.getUsersWithoutAccounts();
            if (users.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(ResponseService.createSuccessResponse("No users without accounts found", null));
            }
            return ResponseEntity.ok(ResponseService.createSuccessResponse("Users without accounts retrieved successfully", users));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to retrieve users without accounts: " + e.getMessage()));
        }
    }

    @GetMapping("/non-students")
    public ResponseEntity<?> findNonStudents() {
        try {
            List<User> users = userService.getNonStudent();
            if (users.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(ResponseService.createSuccessResponse("No non-student users found", null));
            }
            return ResponseEntity.ok(ResponseService.createSuccessResponse("Non-student users retrieved successfully", users));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to retrieve non-student users: " + e.getMessage()));
        }
    }
}