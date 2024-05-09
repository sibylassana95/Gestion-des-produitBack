package com.siby.produits.repository;

import com.siby.produits.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;



/**
 * Interface pour le repository de l'entité User.
 * Elle étend JpaRepository pour bénéficier des fonctionnalités de base fournies par Spring Data JPA.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Recherche un utilisateur par son nom d'utilisateur.
     * 
     * @param username le nom d'utilisateur à rechercher.
     * @return l'utilisateur correspondant, ou null si aucun utilisateur ne correspond.
     */
		User findByUsername(String username);

    /**
     * Recherche un utilisateur par son adresse email.
     * 
     * @param email l'adresse email à rechercher.
     * @return un Optional contenant l'utilisateur correspondant, ou un Optional vide si aucun utilisateur ne correspond.
     */
		Optional<User> findByEmail(String email);

}
