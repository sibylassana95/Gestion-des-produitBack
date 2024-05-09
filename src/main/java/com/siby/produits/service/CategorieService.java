package com.siby.produits.service;

import com.siby.produits.dto.CategorieDTO;
import com.siby.produits.dto.ProduitsDTO;
import com.siby.produits.model.Categorie;

import java.util.List;

public interface CategorieService {

	
	
		CategorieDTO saveCategorie(CategorieDTO app);

		CategorieDTO updateCategorie(CategorieDTO app);

		
		CategorieDTO getCategorie(Long id);

		
		List<CategorieDTO> getAllCategories();

		
		void deleteProfil(Categorie app);

		
		void deleteCategorieById(Long id);

		
		CategorieDTO convertEntityToDto(Categorie app);

		List<ProduitsDTO> getProduitsByCategorie(Long categorieId);
		Categorie convertDtoToEntity(CategorieDTO categorieDTO);
}
