package com.example.sms.Services;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyProperties.AssertingParty.Verification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sms.Models.Account;
import com.example.sms.Models.User;
import com.example.sms.Models.VerificationToken;
import com.example.sms.Repositories.AccountRepository;
import com.example.sms.Repositories.UserRepository;
import com.example.sms.Repositories.VerificationTokenRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailSender emailSender;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    public Account createAccountByNationalId(String nationalId, String email, String password) {
        // Find user
        User user = userRepository.findByNationalId(nationalId)
                .orElseThrow(() -> new RuntimeException("User not found with national ID: " + nationalId));

        // Verify account doesn't exist
        if (accountRepository.existsById(user.getUserId())) {
            throw new RuntimeException("Account already exists for this user");
        }

        // Create account (initially inactive)
        Account account = new Account();
        account.setUser(user);
        account.setEmail(email);
        account.setPassword(passwordEncoder.encode(password));
        account.setStatus("Inactive"); // Account is inactive until verified
        account.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        Account savedAccount = accountRepository.save(account);

        // Send verification email
        sendVerificationEmail(savedAccount);

        return savedAccount;
    }

    public void sendVerificationEmail(Account account) {
        // Create verification token
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationToken.setAccount(account);
        verificationToken.setExpiryDate(calculateExpiryDate(24 * 60)); // 24 hours
        verificationTokenRepository.save(verificationToken);

        // Create verification link
        String verificationLink = "http://localhost:8080/api/v1/accounts/verify/" + verificationToken.getToken();

        // Create email content
        String emailContent = String.format("""
                <!DOCTYPE html>
                <html>
                <head>
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <style>
                        body {
                            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                            line-height: 1.6;
                            color: #333;
                            margin: 0;
                            padding: 0;
                            background-color: #f5f5f5;
                        }
                        .email-container {
                            max-width: 600px;
                            margin: 20px auto;
                            border-radius: 10px;
                            overflow: hidden;
                            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
                        }
                        .header {
                            background-color: #d32f2f;
                            color: white;
                            padding: 25px;
                            text-align: center;
                        }
                        .header h2 {
                            margin: 0;
                            font-size: 24px;
                            font-weight: 600;
                        }
                        .content {
                            padding: 30px;
                            background-color: white;
                        }
                        .content h3 {
                            color: #d32f2f;
                            margin-top: 0;
                            font-size: 20px;
                        }
                        .content p {
                            margin-bottom: 15px;
                            font-size: 16px;
                        }
                        .button {
                            display: inline-block;
                            padding: 12px 25px;
                            background-color: #d32f2f;
                            color: white !important;
                            text-decoration: none;
                            border-radius: 5px;
                            font-weight: 600;
                            margin: 20px 0;
                            text-align: center;
                            transition: background-color 0.3s ease;
                        }
                        .button:hover {
                            background-color: #b71c1c;
                        }
                        .button-container {
                            text-align: center;
                            margin: 25px 0;
                        }
                        .footer {
                            padding: 20px;
                            background-color: #f5f5f5;
                            text-align: center;
                            font-size: 12px;
                            color: #777;
                            border-top: 1px solid #e0e0e0;
                        }
                        @media only screen and (max-width: 600px) {
                            .email-container {
                                margin: 0;
                                border-radius: 0;
                            }
                            .content {
                                padding: 20px;
                            }
                            .button {
                                padding: 10px 20px;
                                font-size: 14px;
                            }
                        }
                        .divider {
                            height: 1px;
                            background-color: #e0e0e0;
                            margin: 20px 0;
                        }
                        .highlight-box {
                            background-color: #ffebee;
                            border-left: 4px solid #d32f2f;
                            padding: 15px;
                            margin: 20px 0;
                            border-radius: 0 4px 4px 0;
                        }
                    </style>
                </head>
                <body>
                    <div class="email-container">
                        <div class="header">
                            <h2>LMS Team Platform</h2>
                        </div>
                        <div class="content">
                            <h3>Welcome!</h3>
                            <p>Thank you for registering. Please click the button below to verify your account:</p>
                            <div class="button-container">
                                <a href="%s" class="button">Verify Account</a>
                            </div>
                            <p>If the button doesn't work, copy and paste this link into your browser:</p>
                            <p><small>%s</small></p>
                            <div class="divider"></div>
                            <p>Best regards,</p>
                            <p><strong>The LMS Team</strong></p>
                        </div>
                        <div class="footer">
                            <p>© 2025 LMS Team Platform. All rights reserved.</p>
                            <p>This is an automated message, please do not reply directly to this email.</p>
                        </div>
                    </div>
                </body>
                </html>
                """, verificationLink, verificationLink);

        // Send email using your existing EmailSender
        emailSender.sendHtmlEmail(
                account.getEmail(),
                "Verify Your LMS Platform Account",
                emailContent);
    }

    public Account verifyAccount(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);

        if (verificationToken.getExpiryDate().before(new java.util.Date())) {
            throw new RuntimeException("Verification token has expired");
        }

        Account account = verificationToken.getAccount();
        account.setStatus("Active");
        accountRepository.save(account);

        // Delete the used token
        verificationTokenRepository.delete(verificationToken);

        return account;
    }

private java.sql.Date calculateExpiryDate(int expiryTimeInMinutes) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(new java.util.Date());
    cal.add(Calendar.MINUTE, expiryTimeInMinutes);
    return new java.sql.Date(cal.getTimeInMillis());
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

    public void activateAccount(Integer userId) {
        Account account = accountRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Account not found with userId: " + userId));

        account.setStatus("Active");
        accountRepository.save(account); // save the updated account
    }

    public void changePassword(Integer userId, String newPassword) throws AccountNotFoundException {
    Account account = accountRepository.findById(userId)
            .orElseThrow(() -> new AccountNotFoundException("Account not found with userId: " + userId));

    // Validate new password
    if (newPassword == null || newPassword.trim().isEmpty()) {
        throw new IllegalArgumentException("Password cannot be empty");
    }

    // Hash the new password before storing
    String encodedPassword = passwordEncoder.encode(newPassword);

    // Update account
    account.setPassword(encodedPassword);
    account.setLastReset(Timestamp.valueOf(LocalDateTime.now()));
    accountRepository.save(account);

    // Send password change confirmation email
    sendPasswordResetEmail(account);
}

public void sendPasswordResetEmail(Account account) {
    // Create reset token
    VerificationToken resetToken = new VerificationToken();
    resetToken.setToken(UUID.randomUUID().toString());
    resetToken.setAccount(account);
    resetToken.setExpiryDate(calculateExpiryDate(60)); // 1 hour expiry
    verificationTokenRepository.save(resetToken);

    // Create reset link with account ID
    String resetLink = String.format("http://localhost:8080/reset-password?token=%s&accountId=%d", 
        resetToken.getToken(), 
        account.getAccountId());

    // Email content with account ID reference
    String emailContent = String.format("""
        <div class="content">
            <h3>Password Reset Request</h3>
            <p>Hello %s,</p>
            <p>We received a request to reset your password (Account ID: %d).</p>
            
            <div class="button-container">
                <a href="%s" class="button">Reset Password</a>
            </div>
            
            <div class="highlight-box">
                <p><strong>Security Notice:</strong> This link will expire in 1 hour.</p>
                <p>If you didn't request this change, please contact support immediately.</p>
            </div>
        </div>
        """,
        account.getUser().getFirstName(),
        account.getAccountId(),
        resetLink
    );

    emailSender.sendHtmlEmail(
        account.getEmail(),
        "Password Reset Request for Account ID: " + account.getAccountId(),
        emailContent
    );
}
public void resetPasswordWithToken(String token, Integer accountId, String newPassword) {
    // Verify token
    VerificationToken resetToken = verificationTokenRepository.findByToken(token);    
    if (resetToken.getExpiryDate().before(new java.util.Date())) {
        throw new RuntimeException("Reset token has expired");
    }

    // Verify account matches
    if (!resetToken.getAccount().getAccountId().equals(accountId)) {
        throw new RuntimeException("Token does not match account");
    }

    // Validate new password
    if (newPassword == null || newPassword.length() < 8) {
        throw new IllegalArgumentException("Password must be at least 8 characters");
    }

    // Update password
    Account account = resetToken.getAccount();
    account.setPassword(passwordEncoder.encode(newPassword));
    account.setLastReset(Timestamp.valueOf(LocalDateTime.now()));
    accountRepository.save(account);
    
    // Delete the used token
    verificationTokenRepository.delete(resetToken);
    
    // Send confirmation email
    sendPasswordChangeConfirmation(account);
}

public void sendPasswordResetVerificationCode(String email) {
    Account account = accountRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Account not found with email: " + email));

    // Generate a 6-digit verification code
    String verificationCode = String.format("%06d", new Random().nextInt(999999));

    // Create verification token with the code
    VerificationToken verificationToken = new VerificationToken();
    verificationToken.setToken(verificationCode);
    verificationToken.setAccount(account);
    verificationToken.setExpiryDate(calculateExpiryDate(15)); // 15 minutes expiry
    verificationTokenRepository.save(verificationToken);

    // Send the verification code via email
    String emailContent = String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <style>
                    body {
                        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                        line-height: 1.6;
                        color: #333;
                        margin: 0;
                        padding: 0;
                        background-color: #f5f5f5;
                    }
                    .email-container {
                        max-width: 600px;
                        margin: 20px auto;
                        border-radius: 10px;
                        overflow: hidden;
                        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
                    }
                    .header {
                        background-color: #d32f2f;
                        color: white;
                        padding: 25px;
                        text-align: center;
                    }
                    .header h2 {
                        margin: 0;
                        font-size: 24px;
                        font-weight: 600;
                    }
                    .content {
                        padding: 30px;
                        background-color: white;
                    }
                    .content h3 {
                        color: #d32f2f;
                        margin-top: 0;
                        font-size: 20px;
                    }
                    .content p {
                        margin-bottom: 15px;
                        font-size: 16px;
                    }
                    .verification-code {
                        font-size: 24px;
                        font-weight: bold;
                        letter-spacing: 3px;
                        text-align: center;
                        margin: 20px 0;
                        padding: 15px;
                        background-color: #ffebee;
                        border-radius: 5px;
                        border: 1px dashed #d32f2f;
                    }
                    .footer {
                        padding: 20px;
                        background-color: #f5f5f5;
                        text-align: center;
                        font-size: 12px;
                        color: #777;
                        border-top: 1px solid #e0e0e0;
                    }
                    .highlight-box {
                        background-color: #ffebee;
                        border-left: 4px solid #d32f2f;
                        padding: 15px;
                        margin: 20px 0;
                        border-radius: 0 4px 4px 0;
                    }
                </style>
            </head>
            <body>
                <div class="email-container">
                    <div class="header">
                        <h2>LMS Team Platform - Password Reset</h2>
                    </div>
                    <div class="content">
                        <h3>Password Reset Verification Code</h3>
                        <p>Hello %s,</p>
                        <p>We received a request to reset your password. Please use the following verification code:</p>
                        
                        <div class="verification-code">%s</div>
                        
                        <div class="highlight-box">
                            <p><strong>Security Notice:</strong></p>
                            <ul>
                                <li>This code will expire in 15 minutes</li>
                                <li>Do not share this code with anyone</li>
                                <li>If you didn't request this, please ignore this email</li>
                            </ul>
                        </div>
                        
                        <p>Best regards,</p>
                        <p><strong>The LMS Team</strong></p>
                    </div>
                    <div class="footer">
                        <p>© 2025 LMS Team Platform. All rights reserved.</p>
                        <p>This is an automated message, please do not reply directly to this email.</p>
                    </div>
                </div>
            </body>
            </html>
            """, 
            account.getUser().getFirstName(), 
            verificationCode);

    emailSender.sendHtmlEmail(
            account.getEmail(),
            "LMS Platform - Password Reset Verification Code",
            emailContent);
}

public boolean verifyResetCode(String email, String code) {
    Account account = accountRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Account not found with email: " + email));

    VerificationToken verificationToken = verificationTokenRepository.findByTokenAndAccount(code, account)
            .orElseThrow(() -> new RuntimeException("Invalid verification code"));

    if (verificationToken.getExpiryDate().before(new java.util.Date())) {
        throw new RuntimeException("Verification code has expired");
    }

    // Code is valid, delete it so it can't be used again
    verificationTokenRepository.delete(verificationToken);
    
    return true;
}

public void resetPasswordWithVerification(String email, String verificationCode, String newPassword) {
    // First verify the code
    if (!verifyResetCode(email, verificationCode)) {
        throw new RuntimeException("Invalid or expired verification code");
    }

    // Proceed with password reset
    Account account = accountRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Account not found with email: " + email));

    // Validate new password
    if (newPassword == null || newPassword.length() < 8) {
        throw new IllegalArgumentException("Password must be at least 8 characters");
    }

    // Update password
    account.setPassword(passwordEncoder.encode(newPassword));
    account.setLastReset(Timestamp.valueOf(LocalDateTime.now()));
    accountRepository.save(account);
    
    // Send confirmation email
    sendPasswordChangeConfirmation(account);
}

private void sendPasswordChangeConfirmation(Account account) {
    String emailContent = String.format("""
        <div class="content">
            <h3>Password Changed Successfully</h3>
            <p>Hello %s,</p>
            <p>Your password for account ID: %d has been successfully changed.</p>
            
            <div class="highlight-box">
                <p><strong>Security Notice:</strong> If you didn't make this change, please contact support immediately.</p>
            </div>
            
            <p>Date of change: %s</p>
        </div>
        """,
        account.getUser().getFirstName(),
        account.getAccountId(),
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
    );

    emailSender.sendHtmlEmail(
        account.getEmail(),
        "Password Changed for Account ID: " + account.getAccountId(),
        emailContent
    );
}
    public Optional<Account> login(String email, String password) {
        return accountRepository.findByEmail(email)
                .filter(account -> "active".equalsIgnoreCase(account.getStatus())) // Check status
                .filter(account -> passwordEncoder.matches(password, account.getPassword()));
    }

    public Long getCountOfAccounts() {
        return accountRepository.count();
    }
    public Optional<Account> findByEmail(String email){
        return accountRepository.findByEmail(email);
    }

}
