package com.example.sms.Repositories;

import com.example.sms.Models.Account;
import com.example.sms.Models.VerificationToken;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
    Optional<VerificationToken> findByTokenAndAccount(String token, Account account);
}