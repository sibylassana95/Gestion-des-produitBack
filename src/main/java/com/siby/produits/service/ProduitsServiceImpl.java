package com.siby.produits.service;

import com.siby.produits.dto.ProduitsDTO;
import com.siby.produits.model.Categorie;
import com.siby.produits.model.Produits;
import com.siby.produits.repository.CategorieRepository;
import com.siby.produits.repository.ImageRepository;
import com.siby.produits.repository.ProduitsRepository;

import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProduitsServiceImpl implements ProduitsService {

	@Autowired
	ProduitsRepository produitRepository;

	@Autowired
	ImageRepository imageRepository;
	@Autowired
	ModelMapper modelMapper;

	/**
	 * Convertit une entité application en DTO.
	 * 
	 * @param app L'entité application à convertir.
	 * @return Le DTO de l'application converti.
	 */
	@Override
	public ProduitsDTO convertEntityToDto(Produits app) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		ProduitsDTO produitsDTO = modelMapper.map(app, ProduitsDTO.class);
		return produitsDTO;
	}

	/**
	 * Convertit un DTO d'application en entité.
	 * 
	 * @param applicationDTO Le DTO de l'application à convertir.
	 * @return L'entité application convertie.
	 */
	@Override
	public Produits convertDtoToEntity(ProduitsDTO produitsDTO) {
		Produits produits = new Produits();
		produits = modelMapper.map(produitsDTO, Produits.class);
		return produits;
	}

	@Override
	public ProduitsDTO saveProduits(ProduitsDTO app) {
		return convertEntityToDto(produitRepository.save(convertDtoToEntity(app)));
	}

	/*
	 * @Override public Produit updateProduit(Produit p) { return
	 * produitRepository.save(p);
	 * 
	 * }
	 */

	@Override
	public ProduitsDTO updateProduits(ProduitsDTO app) {
		Long oldProdImageId = this.getProduits(app.getIdProduits()).getImage().getIdImage();
		Long newProdImageId = app.getImage().getIdImage();
		ProduitsDTO appUpdated = convertEntityToDto(produitRepository.save(convertDtoToEntity(app)));
		if (oldProdImageId != newProdImageId) // si l'image a été modifiée
			imageRepository.deleteById(oldProdImageId);
		return appUpdated;
	}

	@Override
	public void deleteProduits(Produits p) {
		produitRepository.delete(p);

	}

	@Override
	public void deleteProduitsById(Long id) {

		// supprimer l'image avant de supprimer le produit
		ProduitsDTO p = getProduits(id);
		try {
			Files.delete(Paths.get(System.getProperty("user.home") + "/images/" + p.getImage()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		produitRepository.deleteById(id);

	}

	@Override
	public ProduitsDTO getProduits(Long id) {
		return convertEntityToDto(produitRepository.findById(id).get());

	}

	@Override
	public List<ProduitsDTO> getAllProduits() {
		return produitRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
	}

	@Override
	public List<ProduitsDTO> findByCategoryId(Long categoryId) {
		return produitRepository.findByCategoryId(categoryId).stream().map(this::convertEntityToDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<Produits> findByOrderByNomProduitsAsc() {
		return produitRepository.findByOrderByNomProduitsAsc();
	}

	@Override
	public List<Produits> trierProduitsNomsPrix() {
		return produitRepository.trierProduitsNomsPrix();
	}

	@Override
	public List<Produits> findByNomProduits(String nom) {
		return produitRepository.findByNomProduits(nom);
	}

	@Override
	public List<Produits> findByNomProduitsContains(String nom) {
		return produitRepository.findByNomProduitsContains(nom);
	}

	@Override
	public List<Produits> findByNomPrix(String nom, Double prix) {
		return produitRepository.findByNomPrix(nom, prix);
	}

}
