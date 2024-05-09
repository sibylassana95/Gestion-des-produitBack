package com.siby.produits.register;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository pour les tokens de vérification.
 */
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    
    /**
     * Trouve un token de vérification par son contenu.
     * @param token Le contenu du token de vérification.
     * @return Le token de vérification trouvé.
     */
    VerificationToken findByToken(String token);
}
