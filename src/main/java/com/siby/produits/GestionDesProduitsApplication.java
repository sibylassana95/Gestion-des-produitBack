package com.siby.produits;


import javax.management.relation.Role;




@SpringBootApplication
public class GestionDesProduitsApplication  {
	@Autowired
	private RepositoryRestConfiguration repositoryRestConfiguration;
	
	@Autowired
	UserService userService;
	@Autowired
	RoleRepository roleRep;
	public static void main(String[] args) {
		
		SpringApplication.run(GestionDesProduitsApplication.class, args);
	}
	
	 /**
     * Bean pour mapper les modèles.
     * 
     * @return Un nouvel objet ModelMapper.
     */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	
	
	/**
	 * Ajouter les rôles s'ils n'existent pas
	 * Ajouter les utilisateurs s'ils n'existent pas
	 * Ajouter le rôle ADMIN à l'utilisateur admin
	 */
	@PostConstruct 
	void init_users() {
	    if (roleRep.findByRole("ADMIN") == null) {
	        userService.addRole(new Role(null,"ADMIN"));
	    }
	    if (roleRep.findByRole("USER") == null) {
	        userService.addRole(new Role(null,"USER"));
	    }
	    
	    if (userService.findUserByUsername("user") == null) {
	        User admin = new User(null,"admin","pass",true,null,null);
	        userService.saveUser(admin);
	        
	        userService.addRoleToUser("admin", "ADMIN");
	    }
	}
	 
	 
	
	/**
     * Bean pour encoder les mots de passe.
     * 
     * @return Un nouvel objet BCryptPasswordEncoder.
     */
	@Bean
	BCryptPasswordEncoder getBCE() {
		return new BCryptPasswordEncoder();

	}

}
