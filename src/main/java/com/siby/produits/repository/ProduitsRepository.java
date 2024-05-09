package com.siby.produits.repository;

import com.siby.produits.model.Categorie;
import com.siby.produits.model.Produits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;



public interface ProduitsRepository extends JpaRepository<Produits, Long> {
	
	
	  List<Produits> findByNomProduits(String nom); List<Produits>
	  findByNomProduitsContains(String nom);
	  
	  @Query("select p from Produits p where p.nomProduits like %:nom and p.prixProduits > :prix"
	  ) List<Produits> findByNomPrix (@Param("nom") String nom,@Param("prix")
	  Double prix);
	 
    
	@Query("SELECT p FROM Produits p WHERE p.categorie.idCat = :categoryId")
    List<Produits> findByCategoryId(@Param("categoryId") Long categoryId);
	
	
	  List<Produits> findByOrderByNomProduitsAsc();
	  
	  @Query("select p from Produits p order by p.nomProduits ASC, p.prixProduits DESC"
	  ) List<Produits> trierProduitsNomsPrix ();
	 

}
