package com.siby.produits.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Classe représentant les détails d'une erreur.
 */
@Data @AllArgsConstructor @NoArgsConstructor
public class ErrorDetails {
    private LocalDateTime timestamp; // Le moment où l'erreur s'est produite
    private String message; // Le message d'erreur
    private String path; // Le chemin où l'erreur s'est produite
    private String errorCode; // Le code d'erreur

}
