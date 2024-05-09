package com.siby.produits.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * Service d'envoi d'emails.
 */
@AllArgsConstructor
@Service
public class EmailService implements EmailSender {

    private final JavaMailSender mailSender;

    /**
     * Envoie un email.
     * @param to L'adresse email du destinataire.
     * @param email Le contenu de l'email.
     */
    @Override
    public void sendEmail(String to, String email) {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirmer Votre email");
            helper.setFrom("sibyamara95@gmail.com");

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IllegalStateException("echec d'envoi du email");
        }

    }
}
