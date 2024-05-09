package com.siby.produits.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

/**
 * Gestionnaire global des exceptions.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Gère l'exception lancée quand un email existe déjà.
     * @param exception L'exception lancée.
     * @param webRequest La requête web.
     * @return Les détails de l'erreur.
     */
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorDetails> handleEmailAlreadyExistsException(EmailAlreadyExistsException exception,
            WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
                webRequest.getDescription(false), "USER_EMAIL_ALREADY_EXISTS");
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    /**
     * Gère l'exception lancée quand un email n'existe pas .
     * @param exception L'exception lancée.
     * @param webRequest La requête web.
     * @return Les détails de l'erreur.
     */
    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleEmailNotFoundException(EmailNotFoundException exception,
            WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
                webRequest.getDescription(false), "USER_EMAIL_NOT_EXISTS");
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    
   
    
    /**
     * Gère l'exception lancée quand le username existe déjà.
     * @param exception L'exception lancée.
     * @param webRequest La requête web.
     * @return Les détails de l'erreur.
     */
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorDetails> handlePUsernameAlreadyExistsException(UsernameAlreadyExistsException exception,
            WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
                webRequest.getDescription(false), "Username_ALREADY_EXISTS");
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Gère l'exception lancée quand un token est expiré.
     * @param exception L'exception lancée.
     * @param webRequest La requête web.
     * @return Les détails de l'erreur.
     */
    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<ErrorDetails> handleExpiredTokenException(ExpiredTokenException exception,
            WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
                webRequest.getDescription(false), "EXPIRED_TOKEN");
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Gère l'exception lancée quand un token est invalide.
     * @param exception L'exception lancée.
     * @param webRequest La requête web.
     * @return Les détails de l'erreur.
     */
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorDetails> handleInvalidTokenException(InvalidTokenException exception,
            WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
                webRequest.getDescription(false), "INVALID_TOKEN");
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

}
