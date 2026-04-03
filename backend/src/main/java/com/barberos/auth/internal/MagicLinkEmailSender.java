package com.barberos.auth.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
class MagicLinkEmailSender {

    private static final Logger log = LoggerFactory.getLogger(MagicLinkEmailSender.class);

    private final JavaMailSender mailSender;

    MagicLinkEmailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    void send(String email, String verifyUrl) {
        log.debug("Magic link for {}: {}", email, verifyUrl);

        try {
            var message = new SimpleMailMessage();
            message.setTo(email);
            message.setFrom("noreply@barberos.com");
            message.setSubject("Sign in to BarberOS");
            message.setText(
                    "Click the link below to sign in to BarberOS:\n\n"
                            + verifyUrl + "\n\n"
                            + "This link expires in 15 minutes.\n"
                            + "If you didn't request this, you can safely ignore this email."
            );
            mailSender.send(message);
        } catch (Exception e) {
            log.warn("Failed to send magic link email to {} — URL logged above for dev use", email, e);
        }
    }
}
