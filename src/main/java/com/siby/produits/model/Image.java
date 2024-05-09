package com.siby.produits.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Représente la Table Image
 */
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Images")
public class Image {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long idImage ;
 private String name ;
 private String type ;
 @Column( name = "IMAGE" , length = 4048576 )
 @Lob
 private byte[] image;
 @OneToOne
 private Produits produits;


}
