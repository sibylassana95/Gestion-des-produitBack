package com.siby.produits.service;

import com.siby.produits.dto.CategorieDTO;
import com.siby.produits.dto.ProduitsDTO;
import com.siby.produits.model.Categorie;
import com.siby.produits.model.Produits;
import com.siby.produits.repository.CategorieRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@AllArgsConstructor
public class CategorieServiceImpl implements CategorieService {

	@Autowired
	CategorieRepository categorieRepository;
	
	@Autowired
	ModelMapper modelMapper;
	@Autowired
    ProduitsService produitService;
	@Override
	public CategorieDTO saveCategorie(CategorieDTO app) {
		return convertEntityToDto(categorieRepository.save(convertDtoToEntity(app)));
	}

	@Override
	public CategorieDTO updateCategorie(CategorieDTO app) {
		return convertEntityToDto(categorieRepository.save(convertDtoToEntity(app)));
	}

	@Override
	public CategorieDTO getCategorie(Long id) {
		return convertEntityToDto(categorieRepository.findById(id).get());
	}

	
	@Override
	public List<CategorieDTO> getAllCategories() {
		return categorieRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
	}

	
	@Override
	public void deleteProfil(Categorie app) {
		
		categorieRepository.delete(app);
		
	}

	
	@Override
	public void deleteCategorieById(Long id) {
		categorieRepository.deleteById(id);
	}

	
	@Override
	public CategorieDTO convertEntityToDto(Categorie app) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		CategorieDTO categorieDTO = modelMapper.map(app, CategorieDTO.class);
		return categorieDTO;
	}

	
	
	@Override
	public List<ProduitsDTO> getProduitsByCategorie(Long categorieId) {
	    Categorie categorie = categorieRepository.findById(categorieId).orElse(null);
	    if (categorie != null) {
	        return categorie.getProduits().stream()
	                        .map(produit -> produitService.convertEntityToDto(produit))
	                        .collect(Collectors.toList());
	    } else {
	        return Collections.emptyList();
	    }
	}

	@Override
	public Categorie convertDtoToEntity(CategorieDTO categorieDTO) {
		Categorie categorie =new Categorie();
		categorie = modelMapper.map(categorieDTO,Categorie.class);
		return categorie;
	}
	

}
