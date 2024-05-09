package com.siby.produits.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception lancée lorsque le nom d'utilisateur existe déjà.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UsernameAlreadyExistsException extends RuntimeException {
	/**
	 * Message de l'exception.
	 */
    private String message;
    
    /**
     * Constructeur avec un message.
     * @param message Le message d'erreur.
     */
    public UsernameAlreadyExistsException(String message)
    {
        super(message);
    }

}
