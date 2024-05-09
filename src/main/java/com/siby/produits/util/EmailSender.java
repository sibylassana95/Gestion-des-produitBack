package com.siby.produits.util;

/**
 * Interface pour l'envoi d'emails.
 */
public interface EmailSender {
    
    /**
     * Envoie un email.
     * @param to L'adresse email du destinataire.
     * @param email Le contenu de l'email.
     */
    public void sendEmail(String to, String email);
}
