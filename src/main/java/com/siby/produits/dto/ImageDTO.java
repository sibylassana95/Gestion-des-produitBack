package com.siby.produits.dto;

import com.siby.produits.model.Produits;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Représente la Table ImageDTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageDTO {

 private Long idImage ;
 private String name ;
 private String type ;
 private byte[] image;
 private Produits produits;
}
