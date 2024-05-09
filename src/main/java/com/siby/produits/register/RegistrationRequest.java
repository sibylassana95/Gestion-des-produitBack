package com.siby.produits.register;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe représentant une demande d'inscription.
 */
@Data @AllArgsConstructor @NoArgsConstructor
public class RegistrationRequest {
	private String username;
	private String password;
	private String email;

}