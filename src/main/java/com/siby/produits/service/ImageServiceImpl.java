package com.siby.produits.service;

import com.siby.produits.model.Image;
import com.siby.produits.repository.CategorieRepository;
import com.siby.produits.repository.ImageRepository;

import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;



/**
 * Implémentation du service de image.
 */
@AllArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    ImageRepository imageRepository;

    

    /**
     * Télécharge une image.
     * @param file Le fichier de l'image.
     * @return L'image téléchargée.
     * @throws IOException si une erreur d'entrée/sortie se produit.
     */
    @Override
    public Image uplaodImage(MultipartFile file) throws IOException {
        return imageRepository.save(Image.builder().name(file.getOriginalFilename()).type(file.getContentType())
                .image(file.getBytes()).build());
    }

    /**
     * Récupère les détails d'une image.
     * @param id L'identifiant de l'image.
     * @return Les détails de l'image.
     * @throws IOException si une erreur d'entrée/sortie se produit.
     */
    @Override
    public Image getImageDetails(Long id) throws IOException {
        final Optional<Image> dbImage = imageRepository.findById(id);
        return Image.builder().idImage(dbImage.get().getIdImage()).name(dbImage.get().getName())
                .type(dbImage.get().getType()).image(dbImage.get().getImage()).build();
    }

    /**
     * Récupère une image.
     * @param id L'identifiant de l'image.
     * @return L'image récupérée.
     * @throws IOException si une erreur d'entrée/sortie se produit.
     */
    @Override
    public ResponseEntity<byte[]> getImage(Long id) throws IOException {
        final Optional<Image> dbImage = imageRepository.findById(id);
        return ResponseEntity.ok().contentType(MediaType.valueOf(dbImage.get().getType()))
                .body(dbImage.get().getImage());
    }

    /**
     * Supprime une image.
     * @param id L'identifiant de l'image à supprimer.
     */
    @Override
    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }
}
