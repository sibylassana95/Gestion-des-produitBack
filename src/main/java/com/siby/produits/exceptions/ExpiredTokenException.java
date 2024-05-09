package com.siby.produits.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception lancée lorsque le token est expiré.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
/**
 * Classe représentant une exception pour un token expiré. Elle hérite de la
 * classe RuntimeException.
 */
public class ExpiredTokenException extends RuntimeException {
	/**
	 * Message de l'exception.
	 */
	private String message;

	/**
	 * Constructeur de l'exception.
	 *
	 * @param message le message de l'exception.
	 */
	public ExpiredTokenException(String message) {
		super(message);
	}

}