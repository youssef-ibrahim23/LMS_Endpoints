package com.example.sms.Controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sms.Models.VerificationToken;
import com.example.sms.Repositories.VerificationTokenRepository;
import com.example.sms.Services.AccountService;
@Controller
@RequestMapping("/reset-password")
public class PasswordResetController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @GetMapping
    public String showResetForm(
            @RequestParam String token,
            @RequestParam Integer accountId,
            Model model) {
        
        try {
            // Verify token and account ID match
            VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
            
            if (verificationToken.getExpiryDate().before(new Date())) {
                throw new RuntimeException("Token has expired");
            }
            
            if (!verificationToken.getAccount().getAccountId().equals(accountId)) {
                throw new RuntimeException("Invalid account for this reset token");
            }
            
            model.addAttribute("token", token);
            model.addAttribute("accountId", accountId);
            return "reset-password"; // This should match your template name exactly
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "reset-error"; // Make sure this template exists too
        }
    }

    @PostMapping
    public String handlePasswordReset(
        @RequestParam String token,
        @RequestParam Integer accountId,
        @RequestParam String newPassword,
        Model model) {
        
        try {
            accountService.resetPasswordWithToken(token, accountId, newPassword);
            return "redirect:/reset-success"; // Make sure this template exists
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "reset-error";
        }
    }
}