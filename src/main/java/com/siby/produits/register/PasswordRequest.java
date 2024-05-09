package com.siby.produits.register;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe représentant une demande de réinitialisation du mot de passe.
 */
@Data @AllArgsConstructor @NoArgsConstructor
public class PasswordRequest {
	private String email;
}
