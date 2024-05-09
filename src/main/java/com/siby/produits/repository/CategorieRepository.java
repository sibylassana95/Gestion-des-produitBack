package com.siby.produits.repository;

import com.siby.produits.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


public interface CategorieRepository extends JpaRepository<Categorie, Long> {

}
