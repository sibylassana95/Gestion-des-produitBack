package com.siby.produits.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produits {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProduits;
	private int quantite;
	private String nomProduits;
	private Double prixProduits;
	private Date dateCreation;

	@ManyToOne
	private Categorie categorie;
	@JsonIgnore
	@OneToOne
	private Image image;
}
