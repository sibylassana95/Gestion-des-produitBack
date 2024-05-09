package com.siby.produits.controller;

import com.siby.produits.model.Image;
import com.siby.produits.service.ImageService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Contrôleur REST qui gère les requêtes HTTP pour les entités Image.
 * Il permet le Cross-Origin Resource Sharing (CORS) pour permettre les requêtes AJAX provenant de n'importe quel domaine.
 */
@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageRestController {
    @Autowired
    ImageService imageService ;

    /**
     * Charge une image sur le serveur.
     * @param file le fichier image à charger
     * @return un objet Image représentant l'image chargée
     * @throws IOException si une erreur se produit lors de la lecture du fichier
     */
    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Image uploadImage(@RequestParam("image")MultipartFile file) throws IOException {
        return imageService.uplaodImage(file);
    }

    /**
     * Récupère les détails d'une image par son identifiant.
     * @param id l'identifiant de l'image à récupérer
     * @return un objet Image représentant l'image récupérée
     * @throws IOException si une erreur se produit lors de la lecture du fichier
     */
    @GetMapping("/get/info/{id}")
    public Image getImageDetails(@PathVariable("id") Long id) throws IOException {
        return imageService.getImageDetails(id) ;
    }

    /**
     * Récupère une image par son identifiant.
     * @param id l'identifiant de l'image à récupérer
     * @return une ResponseEntity contenant l'image sous forme de tableau de bytes
     * @throws IOException si une erreur se produit lors de la lecture du fichier
     */
    @GetMapping("/load/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) throws IOException {
        return imageService.getImage(id);
    }

    /**
     * Supprime une image par son identifiant.
     * @param id l'identifiant de l'image à supprimer
     */
    @DeleteMapping("/del/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteImage(@PathVariable("id") Long id){
        imageService.deleteImage(id);
    }

    /**
     * Met à jour une image existante.
     * @param file le fichier image à charger
     * @return un objet Image représentant l'image mise à jour
     * @throws IOException si une erreur se produit lors de la lecture du fichier
     */
    @PutMapping("/update")
    @ResponseStatus(HttpStatus.CREATED)
    public Image UpdateImage(@RequestParam("image")MultipartFile file) throws IOException {
        return imageService.uplaodImage(file);
    }
}
