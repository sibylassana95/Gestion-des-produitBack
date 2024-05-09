package com.siby.produits.security;

/**
 * Interface pour les paramètres de sécurité.
 */
public interface SecParams {
    /**
     * Le temps d'expiration du token JWT en millisecondes (10 jours).
     */
	public static final long  EXP_TIME = 10*24*60*60*1000;

    /**
     * La clé secrète pour signer le token JWT.
     */
	public static final String SECRET = "sibyamara95@gmail.com";

    /**
     * Le préfixe du token JWT dans le header d'autorisation.
     */
	public static final String PREFIX = "Bearer ";
}
