package com.example.sms.Services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sms.Models.Account;
import com.example.sms.Repositories.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    // Create a new account
    public Account createAccount(Account account) {
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
        return accountRepository.checkIfUserExistsAndTheirRoleAndStatus(email, password);
    }
    public Long getCountOfAccounts(){
        return accountRepository.count();
    }
}
