package com.example.sms.Services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sms.Models.Account;
import com.example.sms.Models.User;
import com.example.sms.Repositories.AccountRepository;
import com.example.sms.Repositories.UserRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;

     @Transactional
    public Account createAccountByNationalId(String nationalId, String email, String password) {
        // 1. Find user (this attaches user to persistence context)
        User user = userRepository.findByNationalId(nationalId)
                .orElseThrow(() -> new RuntimeException("User not found with national ID: " + nationalId));

        // 2. Verify account doesn't exist
        if (accountRepository.existsById(user.getUserId())) {
            throw new RuntimeException("Account already exists for this user");
        }

        // 3. Create and link account
        Account account = new Account();
        account.setUser(user);  // This sets both the relationship and the ID via @MapsId
        account.setEmail(email);
        account.setPassword(password); // Always encode passwords!
        account.setStatus("Active");
        account.setCreatedAt(Date.valueOf(LocalDate.now()));
        account.setLastReset(Date.valueOf(LocalDate.now()));

        return accountRepository.save(account);
    }
    // Retrieve an account by user ID
    public Optional<Account> getAccountById(Integer userId) {
        return accountRepository.findById(userId);
    }

    // Retrieve all accounts
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Delete an account by user ID
    public void deactivateAccount(Integer userId) {
        Account account = accountRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Account not found with userId: " + userId));

        account.setStatus("InActive");
        accountRepository.save(account); // save the updated account
    }

    public void changePassword(Integer user, String password) {
        Account account = accountRepository.findById(user)
                .orElseThrow(() -> new RuntimeException("Account not found with userId: " + user));
        Date now = Date.valueOf(LocalDate.now());
        account.setPassword(password);
        account.setLastReset(now);
        accountRepository.save(account);
    }

    public Optional<Account> login(String email, String password) {
        return accountRepository.findActiveAccountByCredentials(email, password);
    }

    public Long getCountOfAccounts() {
        return accountRepository.count();
    }
}
