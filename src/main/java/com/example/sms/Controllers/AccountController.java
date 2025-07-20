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
import org.springframework.web.bind.annotation.RestController;

import com.example.sms.DTO.AccountCreationRequest;
import com.example.sms.DTO.LoginRequest;
import com.example.sms.Models.Account;
import com.example.sms.Services.AccountService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
public ResponseEntity<?> createAccountByNationalId(@RequestBody AccountCreationRequest request) {
    try {
        Account account = accountService.createAccountByNationalId(
            request.getNationalId(),
            request.getEmail(),
            request.getPassword()
        );
        return ResponseEntity.ok(account);
    } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body(Map.of(
            "error", e.getMessage(),
            "timestamp", LocalDateTime.now()
        ));
    }
}

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Integer id) {
        return accountService.getAccountById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> changePassword(@PathVariable Integer id, @RequestBody Map<String, String> password) {
        String pass = password.get("password");
        accountService.changePassword(id, pass);
        return ResponseEntity.ok("Password has been reset successfully");
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<String> deactivateAccount(@PathVariable Integer id) {
        accountService.deactivateAccount(id);
        return ResponseEntity.ok("Account is deactivated");
    }

    @PutMapping("/activate/{id}")
    public ResponseEntity<String> activeAccount(@PathVariable Integer id) {
        accountService.activateAccount(id);
        return ResponseEntity.ok("Account is deactivated");
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
    public Long getCount() {
        return accountService.getCountOfAccounts();
    }
}
