package com.example.sms.Controllers;

import java.time.LocalDateTime;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.sms.DTO.AccountCreationRequest;
import com.example.sms.DTO.LoginRequest;
import com.example.sms.DTO.PasswordResetRequest;
import com.example.sms.DTO.VerificationCodeRequest;
import com.example.sms.Models.Account;
import com.example.sms.Services.AccountService;
import com.example.sms.Services.ResponseService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<?> createAccountByNationalId(@RequestBody AccountCreationRequest request) {
        try {
            // Validate input
            if (request.getNationalId() == null || request.getNationalId().isEmpty()) {
                throw new IllegalArgumentException("National ID is required");
            }
            if (request.getEmail() == null || request.getEmail().isEmpty()) {
                throw new IllegalArgumentException("Email is required");
            }
            if (request.getPassword() == null || request.getPassword().isEmpty()) {
                throw new IllegalArgumentException("Password is required");
            }

            // Create account and send verification email
            Account account = accountService.createAccountByNationalId(
                    request.getNationalId(),
                    request.getEmail(),
                    request.getPassword());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Account created successfully. Please check your email for verification.");
            response.put("accountId", account.getAccountId());
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ResponseService.createErrorResponse("An unexpected error occurred"));
        }
    }

    @GetMapping("/verify/{token}")
    public ResponseEntity<?> verifyAccount(@PathVariable String token) {
        try {
            Account verifiedAccount = accountService.verifyAccount(token);
            return ResponseEntity.ok(ResponseService.createSuccessResponse(
                    "Account verified successfully",
                    verifiedAccount));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ResponseService.createErrorResponse("Failed to verify account"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable Integer id) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("Invalid account ID");
            }

            Optional<Account> account = accountService.getAccountById(id);
            if (account.isPresent())
                return ResponseEntity.ok(account);
            else
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseService.createErrorResponse("Attendance record not found with ID: " + id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ResponseService.createErrorResponse("Failed to retrieve account"));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllAccounts() {
        try {
            List<Account> accounts = accountService.getAllAccounts();
            return ResponseEntity.ok(accounts);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ResponseService.createErrorResponse("Failed to retrieve accounts"));
        }
    }

     @PostMapping("/request-reset")
    public ResponseEntity<?> requestPasswordReset(@RequestBody VerificationCodeRequest request) {
        try {
            // Validate email input
            if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                throw new IllegalArgumentException("Email is required");
            }

            // Send verification code
            accountService.sendPasswordResetVerificationCode(request.getEmail());

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Verification code sent to your email");
            response.put("email", request.getEmail());
            
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ResponseService.createErrorResponse("Failed to process password reset request"));
        }
    }

    @PostMapping("/verify-reset-code")
    public ResponseEntity<?> verifyResetCode(@RequestBody VerificationCodeRequest request) {
        try {
            // Validate inputs
            if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                throw new IllegalArgumentException("Email is required");
            }
            if (request.getCode() == null || request.getCode().trim().isEmpty()) {
                throw new IllegalArgumentException("Verification code is required");
            }

            // Verify the code
            boolean isValid = accountService.verifyResetCode(request.getEmail(), request.getCode());

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Verification code is valid");
            response.put("email", request.getEmail());
            
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ResponseService.createErrorResponse("Failed to verify reset code"));
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPasswordWithVerification(@RequestBody PasswordResetRequest request) {
        try {
            // Validate inputs
            if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                throw new IllegalArgumentException("Email is required");
            }
            if (request.getCode() == null || request.getCode().trim().isEmpty()) {
                throw new IllegalArgumentException("Verification code is required");
            }
            if (request.getNewPassword() == null || request.getNewPassword().trim().isEmpty()) {
                throw new IllegalArgumentException("New password is required");
            }

            // Reset password
            accountService.resetPasswordWithVerification(
                request.getEmail(), 
                request.getCode(), 
                request.getNewPassword()
            );

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Password has been reset successfully");
            response.put("email", request.getEmail());
            
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ResponseService.createErrorResponse("Failed to reset password"));
        }
    }
    
    @PutMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivateAccount(@PathVariable Integer id) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("Invalid account ID");
            }

            accountService.deactivateAccount(id);
            return ResponseEntity.ok(ResponseService.createSuccessResponse("Account has been deactivated", id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ResponseService.createErrorResponse("Failed to deactivate account"));
        }
    }

    @PutMapping("/activate/{id}")
    public ResponseEntity<?> activateAccount(@PathVariable Integer id) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("Invalid account ID");
            }

            accountService.activateAccount(id);
            return ResponseEntity.ok(ResponseService.createSuccessResponse("Account has been activated", 1));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ResponseService.createErrorResponse("Failed to activate account"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest data) {
        String email = data.getEmail();
        String password = data.getPassword();

        Optional<Account> account = accountService.login(email, password);

        if (account.isPresent()) {
            return ResponseEntity.ok(account.get());
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Invalid credentials or account not found");
            response.put("status", "error");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<?> getCount() {
        try {
            Long count = accountService.getCountOfAccounts();
            return ResponseEntity.ok(Map.of("count", count));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ResponseService.createErrorResponse("Failed to get account count"));
        }
    }
}