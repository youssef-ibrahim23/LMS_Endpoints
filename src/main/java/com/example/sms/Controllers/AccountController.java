package com.example.sms.Controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sms.Models.Account;
import com.example.sms.Services.AccountService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        return ResponseEntity.ok(accountService.createAccount(account));
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

    @PostMapping("/login")
public Optional<Account> login(@RequestBody Map<String, String> data) {
    String email = data.get("email");
    String password = data.get("password");
    return accountService.login(email, password);
}

    @GetMapping("/count")
    public Long getCount(){
        return accountService.getCountOfAccounts();
    }
}
