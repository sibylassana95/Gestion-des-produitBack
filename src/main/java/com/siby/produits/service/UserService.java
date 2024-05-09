package com.siby.produits.service;

import com.siby.produits.model.Role;
import com.siby.produits.model.User;
import com.siby.produits.register.RegistrationRequest;

import java.util.List;



public interface UserService {


	// users

	/**
	 * Enregistre un nouvel utilisateur.
	 * 
	 * @param user L'utilisateur à enregistrer
	 * @return L'utilisateur enregistré
	 */
	User saveUser(User user);
	
	

	/**
	 * Recherche un utilisateur par son nom d'utilisateur.
	 * 
	 * @param username Le nom d'utilisateur à rechercher
	 * @return L'utilisateur correspondant
	 */
	User findUserByUsername(String username);

	/**
	 * Ajoute un rôle.
	 * 
	 * @param role Le rôle à ajouter
	 * @return Le rôle ajouté
	 */
	Role addRole(Role role);
    
	/**
	 * Récupère tous les Role.
	 * 
	 * @return La liste de tous les roles
	 */
	List<Role> getAllRoles();
	/**
	 * Ajoute un rôle à un utilisateur.
	 * 
	 * @param username Le nom d'utilisateur
	 * @param rolename Le nom du rôle à ajouter
	 * @return L'utilisateur avec le rôle ajouté
	 */
	User addRoleToUser(String username, String rolename);

	/**
	 * Récupère un utilisateur par son identifiant.
	 * 
	 * @param id L'identifiant de l'utilisateur à récupérer
	 * @return L'utilisateur correspondant
	 */
	User getUser(Long id);

	/**
	 * Récupère tous les utilisateurs.
	 * 
	 * @return La liste de tous les utilisateurs
	 */
	List<User> getAllUsers();

	/**
	 * Met à jour un utilisateur.
	 * 
	 * @param user L'utilisateur à mettre à jour
	 * @return L'utilisateur mis à jour
	 */
	User updateUser(User user);

	/**
	 * Enregistre un nouvel utilisateur lors de l'inscription.
	 * 
	 * @param request La demande d'inscription
	 * @return L'utilisateur enregistré
	 */
	User registerUser(RegistrationRequest request);
	
	 /**
     * Méthode pour envoyer un code de réinitialisation de mot de passe à l'adresse e-mail spécifiée.
     *
     * @param email l'adresse e-mail à laquelle envoyer le code de réinitialisation
     */
	void sendResetPasswordCode(String email);
	/**
     * Méthode pour réinitialiser le mot de passe de l'utilisateur.
     *
     * @param email l'adresse e-mail de l'utilisateur
     * @param code le code de réinitialisation du mot de passe
     * @param newPassword le nouveau mot de passe
     */
	void resetPassword(String email, String code, String newPassword);
	

	/**
	 * Envoie un email à un utilisateur.
	 * 
	 * @param u    L'utilisateur à qui envoyer l'email
	 * @param code Le code d'activation
	 */
	public void sendEmailUser(User u, String code);

	/**
	 * Valide un token de sécurité.
	 * 
	 * @param code Le code à valider
	 * @return L'utilisateur correspondant au token
	 */
	public User validateToken(String code);

}

