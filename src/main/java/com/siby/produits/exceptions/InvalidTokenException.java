package com.siby.produits.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception lancée lorsque le token est invalide.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class InvalidTokenException extends RuntimeException {
	/**
	 * Message de l'exception.
	 */
    private String message;
    
    /**
     * Constructeur avec un message.
     * @param message Le message d'erreur.
     */
    public InvalidTokenException(String message)
    {
        super(message);
    }

}
