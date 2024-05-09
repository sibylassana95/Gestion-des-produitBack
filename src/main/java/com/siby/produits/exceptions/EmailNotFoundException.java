package com.siby.produits.exceptions;


/**
 * Exception lancée lorsque l'email n'existe pas
 */
public class EmailNotFoundException extends RuntimeException{
	/**
	 * Message de l'exception.
	 */
	 private String message;
	    
	    /**
	     * Constructeur avec un message.
	     * @param message Le message d'erreur.
	     */
	    public EmailNotFoundException(String message)
	    {
	        super(message);
	    }

	}