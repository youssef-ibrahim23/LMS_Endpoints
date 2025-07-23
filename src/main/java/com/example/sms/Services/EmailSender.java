package com.example.sms.Services;

import java.util.Properties;

import org.springframework.stereotype.Service;

import jakarta.mail.*;
import jakarta.mail.internet.*;
@Service
public class EmailSender {
    private final String from;
    private final String host;
    private final String username;
    private final String password;

    public EmailSender() {
        this.from = "tmsteam.platform@gmail.com";
        this.host = "smtp.gmail.com";
        this.username = "tmsteam.platform@gmail.com";
        this.password = "rhamiehfsclxaeul";
    }

    public boolean sendEmail(String to, String subject, String body) {
        return sendHtmlEmail(to, subject, createStyledEmail(body));
    }

    public boolean sendHtmlEmail(String to, String subject, String htmlContent) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getInstance(props,
            new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(htmlContent, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            message.setContent(multipart);

            Transport.send(message);
            System.out.println("✓ Email sent successfully to " + to);
            return true;

        } catch (MessagingException e) {
            System.err.println("✗ Failed to send email to " + to + ": " + e.getMessage());
            return false;
        }
    }

private String createStyledEmail(String content) {
    return """
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
                    """ + content + """
                    <div class="button-container">
                        <a href="#" class="button">Verify Account</a>
                    </div>
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
        """;
}
   public static void main(String[] args) {
    EmailSender emailSender = new EmailSender();

    emailSender.sendEmail(
        "bilalfayad72@gmail.com",
        "Welcome to Our Platform",
        "We're excited to have you join our community! Here you'll find all the tools you need for successful learning management."
    );
}

}
