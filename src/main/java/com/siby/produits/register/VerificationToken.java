package com.siby.produits.register;

import com.siby.produits.model.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;


/**
 * Classe représentant un token de vérification.
 */
@Data
@Entity
@NoArgsConstructor
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Date expirationTime;
    private static final int EXPIRATION_TIME = 60;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Constructeur avec token et utilisateur.
     * @param token Le token de vérification.
     * @param user L'utilisateur associé au token.
     */
    public VerificationToken(String token, User user) {
        super();
        this.token = token;
        this.user = user;
        this.expirationTime = this.getTokenExpirationTime();
    }

    /**
     * Constructeur avec token.
     * @param token Le token de vérification.
     */
    public VerificationToken(String token) {
        super();
        this.token = token;
        this.expirationTime = this.getTokenExpirationTime();
    }

    /**
     * Récupère la date d'expiration du token.
     * @return La date d'expiration du token.
     */
    public Date getTokenExpirationTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, EXPIRATION_TIME);
        return new Date(calendar.getTime().getTime());
    }
}
