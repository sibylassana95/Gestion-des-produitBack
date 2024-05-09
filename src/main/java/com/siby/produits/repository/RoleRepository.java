package com.siby.produits.repository;

import com.siby.produits.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;



/**
 * Interface pour le repository de l'entité Role.
 * Elle étend JpaRepository pour bénéficier des fonctionnalités de base fournies par Spring Data JPA.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
	 /**
     * Recherche un Role en utilisant role comme paramètres.
     *
     * @param role  utiliser pour la recherche
     *
     * @return l'entité Role correspondant aux paramètres de recherche
     */
	Role findByRole(String role);
	
}
