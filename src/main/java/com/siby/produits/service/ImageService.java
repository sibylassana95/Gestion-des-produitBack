package com.siby.produits.service;

import com.siby.produits.model.Image;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;



/**
* Interface définissant les opérations disponibles pour la gestion des
* images.
*/
public interface ImageService {
    
    /**
     * Télécharge une image.
     * @param file Le fichier de l'image.
     * @return L'image téléchargée.
     * @throws IOException si une erreur d'entrée/sortie se produit.
     */
    Image uplaodImage(MultipartFile file) throws IOException;
    
    /**
     * Récupère les détails d'une image.
     * @param id L'identifiant de l'image.
     * @return Les détails de l'image.
     * @throws IOException si une erreur d'entrée/sortie se produit.
     */
    Image getImageDetails(Long id) throws IOException;
    
    /**
     * Récupère une image.
     * @param id L'identifiant de l'image.
     * @return L'image récupérée.
     * @throws IOException si une erreur d'entrée/sortie se produit.
     */
    ResponseEntity<byte[]> getImage(Long id) throws IOException;
    
    /**
     * Supprime une image.
     * @param id L'identifiant de l'image à supprimer.
     */
    void deleteImage(Long id);
}
