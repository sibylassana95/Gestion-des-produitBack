package com.siby.produits.dto;

import com.siby.produits.model.Categorie;
import com.siby.produits.model.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProduitsDTO {
	private Long idProduits;
	private String nomProduits;
	private int quantite;
	private Double prixProduits;
	private Date dateCreation;
	private Categorie categorie;
	private Image image;
}
