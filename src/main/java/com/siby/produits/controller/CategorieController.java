package com.siby.produits.controller;

import com.siby.produits.dto.CategorieDTO;

import com.siby.produits.service.CategorieService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(
	    name = "Gestion des produits - CategorieController",
	    description = "Categorie Exposes REST API"
	)
@RequiredArgsConstructor
@RestController
@RequestMapping("/categorie")
public class CategorieController {

	@Autowired
	CategorieService categorieService;
	
	
	@Operation(
	        summary = "Get All Categorie REST API",
	        description = "Get All Categorie REST API is used to get all categories in database"
	    )
	    @ApiResponse(
	        responseCode = "200",
	        description = "HTTP Status 200 SUCCESS"
	    )
	@GetMapping("all")
	public List<CategorieDTO> getAllCategories() {
		return categorieService.getAllCategories();
	}
	 @Operation(
		        summary = "Get CategorieBy Id REST API",
		        description = "Get Categorie  By Id REST API is used to get categorie by id"
		    )
		    @ApiResponse(
		        responseCode = "200",
		        description = "HTTP Status 200 SUCCESS"
		    )
	@GetMapping("/getbyid/{id}")
	public CategorieDTO getProfilById(@PathVariable("id") Long id) {
		return categorieService.getCategorie(id);
	}


    @Operation(
        summary = "Save Categorie  REST API",
        description = "Save Categorie REST API is used to save categorie object in database"
    )
    @ApiResponse(
        responseCode = "201",
        description = "HTTP Status 201 CREATED"
    )
	@PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
	public CategorieDTO createProfil(@RequestBody CategorieDTO categorieDTO) {
		return categorieService.saveCategorie(categorieDTO);
	}

    @Operation(
            summary = "Update Categorie  REST API",
            description = "Update Categorie REST API is used to update categorie object in database"
        )
        @ApiResponse(
        		responseCode = "200",
 		        description = "HTTP Status 200 SUCCESS"
 		    )
        
	@PutMapping("/update")
	public CategorieDTO updateProfil(@RequestBody CategorieDTO categorieDTO) {
		return categorieService.updateCategorie(categorieDTO);
	}
    @Operation(
            summary = "Delete Categorie  REST API",
            description = "Delete Categorie REST API is used to delete categorie object in database"
        )
        @ApiResponse(
        		responseCode = "200",
 		        description = "HTTP Status 200 SUCCESS"
 		    )
	@DeleteMapping("/del/{id}")
	public void deleteProfil(@PathVariable("id") Long id) {
		categorieService.deleteCategorieById(id);
	}

}


