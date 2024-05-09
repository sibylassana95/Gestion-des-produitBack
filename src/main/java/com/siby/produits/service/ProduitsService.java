package com.siby.produits.service;

import com.siby.produits.dto.ProduitsDTO;
import com.siby.produits.model.Categorie;
import com.siby.produits.model.Produits;

import java.util.List;

public interface ProduitsService {

	ProduitsDTO saveProduits(ProduitsDTO p);

	ProduitsDTO updateProduits(ProduitsDTO p);

	void deleteProduits(Produits p);

	void deleteProduitsById(Long id);

	ProduitsDTO getProduits(Long id);

	List<ProduitsDTO> getAllProduits();

	ProduitsDTO convertEntityToDto(Produits app);

	Produits convertDtoToEntity(ProduitsDTO produitsDTO);

	List<Produits> findByNomProduits(String nom);

	List<Produits> findByNomProduitsContains(String nom);

	List<Produits> findByNomPrix(String nom, Double prix);

	List<Produits> findByOrderByNomProduitsAsc();

	List<Produits> trierProduitsNomsPrix();

	List<ProduitsDTO> findByCategoryId(Long categoryId);

}
