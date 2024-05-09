package com.siby.produits.repository;

import com.siby.produits.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;



/**
 * Interface pour le repository de l'entité Image.
 * Elle étend JpaRepository pour bénéficier des fonctionnalités de base fournies par Spring Data JPA.
 */
public interface ImageRepository extends JpaRepository<Image, Long> {
}