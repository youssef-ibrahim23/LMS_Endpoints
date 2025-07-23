package com.example.sms.Controllers;

import com.example.sms.Enums.Roles;
import com.example.sms.Models.Role;
import com.example.sms.Services.RoleService;
import com.example.sms.Services.ResponseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/roles")
@Validated
public class RoleController {

    @Autowired
    private RoleService roleService;

 @GetMapping
public ResponseEntity<?> getAllRoles() {
    try {
        List<Role> roles = roleService.getAllRoles();
        if (roles.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(ResponseService.createSuccessResponse("No roles found", null));
        }
        return ResponseEntity.ok(
            ResponseService.createSuccessResponse("Roles retrieved successfully", roles)
        );
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to retrieve roles"));
    }
}
    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable @NotNull Integer id) {
        try {
            if (id <= 0) {
                return ResponseEntity.badRequest()
                        .body(ResponseService.createErrorResponse("Role ID must be positive"));
            }

            Role role = roleService.getRoleById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Role not found with ID: " + id));
            
            return ResponseEntity.ok(ResponseService.createSuccessResponse("Role retrieved successfully", role));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseService.createErrorResponse("Failed to retrieve role: " + e.getMessage()));
        }
    }

       @PostMapping
    public ResponseEntity<?> createRole(@RequestBody @Valid Role roleInput) {
        try {
            if (roleInput.getRoleName() == null) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Role name is required"));
            }

            // Create new role with just the name
            Role role = new Role();
            role.setRoleName(roleInput.getRoleName()); // Validation happens inside setter

            Role createdRole = roleService.addRole(role);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseService.createSuccessResponse("Role created successfully", createdRole));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to create role: " + e.getMessage()));
        }
    }
}