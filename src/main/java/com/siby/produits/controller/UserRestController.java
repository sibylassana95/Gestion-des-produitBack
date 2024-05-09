package com.siby.produits.controller;

import com.siby.produits.exceptions.EmailNotFoundException;
import com.siby.produits.exceptions.ExpiredTokenException;
import com.siby.produits.exceptions.InvalidTokenException;
import com.siby.produits.model.Role;
import com.siby.produits.model.User;
import com.siby.produits.register.RegistrationRequest;
import com.siby.produits.repository.RoleRepository;
import com.siby.produits.repository.UserRepository;
import com.siby.produits.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Contrôleur REST pour la gestion des utilisateurs.
 */
@RestController
@RequiredArgsConstructor
public class UserRestController {

	@Autowired
	UserRepository userRep;
	@Autowired
	UserService userService;
	
	@Autowired
	RoleRepository rolerepository;
	
	/**
	 * Récupérer tous les utilisateurs.
	 * 
	 * @return une liste de tous les utilisateurs.
	 */
	@GetMapping("/users/all")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public List<User> getAllUsers() {
		return userRep.findAll();
	}

	/**
	 * Récupérer un utilisateur par son identifiant.
	 * 
	 * @param id l'identifiant de l'utilisateur.
	 * @return l'utilisateur correspondant à l'identifiant.
	 */
	@GetMapping("/users/getbyid/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public User getUserById(@PathVariable("id") Long id) {
		return userService.getUser(id);
	}

	/**
	 * Enregistrer un nouvel utilisateur.
	 * 
	 * @param request les informations d'enregistrement de l'utilisateur.
	 * @return l'utilisateur enregistré.
	 */
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public User register(@RequestBody RegistrationRequest request) {
		return userService.registerUser(request);
	}

	/**
	 * Vérifier l'email d'un utilisateur.
	 * 
	 * @param token le token de vérification.
	 * @return l'utilisateur dont l'email a été vérifié.
	 */
	@GetMapping("/verifyEmail/{token}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public User verifyEmail(@PathVariable("token") String token) {
		return userService.validateToken(token);
	}

	/**
	 * Mettre à jour un utilisateur.
	 * 
	 * @param user les nouvelles informations de l'utilisateur.
	 * @return l'utilisateur mis à jour.
	 */
	@PutMapping("/users/update")
	@ResponseStatus(HttpStatus.CREATED)
	public User updateUser(@RequestBody User user) {
		return userService.updateUser(user);
	}

	/**
	 * Enregistrer un nouvel utilisateur.
	 * 
	 * @param user l'utilisateur à enregistrer.
	 * @return l'utilisateur enregistré.
	 */
	@PostMapping("/users/add")
	@ResponseStatus(HttpStatus.CREATED)
	public User saveUser(@RequestBody User user) {
		return userService.saveUser(user);
	}

	/**
	 * Récupérer tous les rôles.
	 * 
	 * @return une liste de tous les rôles.
	 */
	@GetMapping("/role/all")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public List<Role> getAllRoles() {
		return rolerepository.findAll();
	}

	/**
	 * Ajouter un rôle à un utilisateur.
	 * 
	 * @param user l'utilisateur auquel ajouter le rôle.
	 * @return l'utilisateur avec le nouveau rôle.
	 */
	@PostMapping("/role/add")
	@ResponseStatus(HttpStatus.CREATED)
	public User addRoleToUser(@RequestBody User user) {
		return userService.addRoleToUser(user.getUsername(), ((Role) user.getRoles()).getRole());
	}
	
    /**
     * Méthode pour envoyer un e-mail de réinitialisation de mot de passe à l'utilisateur.
     *
     * @param email l'adresse e-mail de l'utilisateur
     * @return une réponse avec un message de succès ou d'erreur
     */
	@PostMapping("/sendmail")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public ResponseEntity<Map<String, String>> resetPassword(@RequestParam String email) {
	    try {
	        userService.sendResetPasswordCode(email);
	        Map<String, String> response = new HashMap<>();
	        response.put("message", "Un e-mail de réinitialisation de mot de passe a été envoyé à l'adresse spécifiée.");
	        return ResponseEntity.ok().body(response);
	    } catch (EmailNotFoundException e) {
	        Map<String, String> response = new HashMap<>();
	        response.put("error", "Adresse e-mail introuvable.");
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (Exception e) {
	        Map<String, String> response = new HashMap<>();
	        response.put("error", "Une erreur s'est produite lors de la réinitialisation du mot de passe.");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}
	 /**
     * Méthode pour réinitialiser le mot de passe de l'utilisateur.
     *
     * @param email l'adresse e-mail de l'utilisateur
     * @param code le code de réinitialisation du mot de passe
     * @param newPassword le nouveau mot de passe
     * @return une réponse avec un message de succès ou d'erreur
     */
	@PostMapping("/reset-password")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public ResponseEntity<Map<String, String>> resetPassword(@RequestParam("email") String email,
	                                                         @RequestParam("code") String code,
	                                                         @RequestParam("newPassword") String newPassword) {
	    try {
	        userService.resetPassword(email, code, newPassword);
	        Map<String, String> response = new HashMap<>();
	        response.put("message", "Mot de passe réinitialisé avec succès.");
	        return ResponseEntity.ok().body(response);
	    } catch (EmailNotFoundException e) {
	    	 Map<String, String> response = new HashMap<>();
		        response.put("error", "Adresse e-mail introuvable.");
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (InvalidTokenException e) {
	    	Map<String, String> response = new HashMap<>();
	        response.put("error", "Token invalide");
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    } catch (ExpiredTokenException e) {
	    	Map<String, String> response = new HashMap<>();
	        response.put("error", "Token expiré");
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        
	    }

	}
}

