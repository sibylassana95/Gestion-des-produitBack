package com.siby.produits.controller;

import com.siby.produits.dto.ProduitsDTO;

import com.siby.produits.service.CategorieService;
import com.siby.produits.service.ProduitsService;

import lombok.RequiredArgsConstructor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/produits")
@RequiredArgsConstructor
public class ProduitsController {
    
    @Autowired
    ProduitsService produitService;
    @Autowired
    private CategorieService categorieService;
    @GetMapping("all")
    public  List<ProduitsDTO> getAllProduits(){
    	return produitService.getAllProduits();
       
    }      

    @GetMapping("/getbyid/{id}")
    public ProduitsDTO getProduits(@PathVariable("id") Long id) {    
            return produitService.getProduits(id);
        
    }
    
    
    @PostMapping("/addprod")
    public ResponseEntity<?> createProduit(@RequestBody ProduitsDTO produit) {
        try {
            ProduitsDTO savedProduit = produitService.saveProduits(produit);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduit);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la création du produit.");
        }
    }

    
    @PutMapping("/updateprod")
    public ResponseEntity<?> updateProduit(@RequestBody ProduitsDTO produit) {
        try {
            ProduitsDTO updatedProduit = produitService.updateProduits(produit);
            return ResponseEntity.ok(updatedProduit);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produit avec l'ID " + produit.getIdProduits() + " n'existe pas.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la mise à jour du produit.");
        }
    }

    @DeleteMapping("/delprod/{id}")
    public ResponseEntity<String> deleteProduit(@PathVariable("id") Long id) {
        try {
            produitService.deleteProduitsById(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produit avec l'ID " + id + " n'existe pas.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la suppression du produit.");
        }
    }
    
    @GetMapping("/prodscat/{categorieId}")
    public ResponseEntity<List<ProduitsDTO>> getProduitsByCategorie(@PathVariable Long categorieId) {
        List<ProduitsDTO> produits = categorieService.getProduitsByCategorie(categorieId);
        if (!produits.isEmpty()) {
            return new ResponseEntity<>(produits, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
   
    
}
