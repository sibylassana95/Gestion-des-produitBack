package com.siby.produits.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 *  Représente la Table User
 */
@Data 
@NoArgsConstructor 
@AllArgsConstructor
@Entity
@Table(name="Utilisateurs")
public class User {	
	@Id 
	@GeneratedValue (strategy=GenerationType.IDENTITY) 
	private Long user_id;

    @Column(unique=true)
	private String username;
	private String password;
	private Boolean enabled;
	@Column(unique=true)
	private String email;
	
	
    @ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="user_role",joinColumns = @JoinColumn(name="user_id") , 
			   inverseJoinColumns = @JoinColumn(name="role_id")) 
	private List<Role> roles; 
    
   
}
