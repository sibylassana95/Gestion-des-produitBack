package com.siby.produits.service;

import com.siby.produits.exceptions.*;
import com.siby.produits.model.Role;
import com.siby.produits.model.User;
import com.siby.produits.register.RegistrationRequest;
import com.siby.produits.register.VerificationToken;
import com.siby.produits.register.VerificationTokenRepository;
import com.siby.produits.repository.CategorieRepository;
import com.siby.produits.repository.RoleRepository;
import com.siby.produits.repository.UserRepository;
import com.siby.produits.util.EmailSender;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;



@Service
@AllArgsConstructor
public class UserServiceImpl  implements UserService{
	
	@Autowired
	UserRepository userRep;

	@Autowired
	RoleRepository roleRep;
	@Autowired
	VerificationTokenRepository verificationTokenRepo;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	EmailSender emailSender;
	@Autowired
	RoleRepository rolerepository;
	/**
	 * Enregistre un nouvel utilisateur.
	 * 
	 * @param user L'utilisateur à enregistrer.
	 * @return L'utilisateur enregistré.
	 */
	@Override
	public User saveUser(User user) {
		Optional<User> optionalUser = userRep.findByEmail(user.getEmail());
		if (optionalUser.isPresent())
			throw new EmailAlreadyExistsException("Email déjà existant!");
		 User username = userRep.findByUsername(user.getUsername());
		    if (username != null) {
		        throw new UsernameAlreadyExistsException("Nom d'utilisateur déjà existant!");
		    }
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		
		Role r = roleRep.findByRole("USER");
		List<Role> roles = new ArrayList<>();
		roles.add(r);
		user.setRoles(roles);
		
		return userRep.save(user);
	}

	/**
	 * Met à jour un utilisateur existant.
	 * 
	 * @param user L'utilisateur à mettre à jour.
	 * @return L'utilisateur mis à jour.
	 */
	@Override
	public User updateUser(User user) {
		
		User existingUser = userRep.findById(user.getUser_id()).orElse(null);
		if (existingUser != null && !user.getPassword().equals(existingUser.getPassword())) {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		}
		return userRep.save(user);
	}

	/**
	 * Ajoute un rôle à un utilisateur.
	 * 
	 * @param username Le nom d'utilisateur.
	 * @param rolename Le nom du rôle à ajouter.
	 * @return L'utilisateur avec le nouveau rôle.
	 */
	@Override
	public User addRoleToUser(String username, String rolename) {
		User usr = userRep.findByUsername(username);
		Role r = roleRep.findByRole(rolename);

		usr.getRoles().add(r);
		return usr;
	}

	/**
	 * Ajoute un nouveau rôle.
	 * 
	 * @param role Le rôle à ajouter.
	 * @return Le rôle ajouté.
	 */
	@Override
	public Role addRole(Role role) {
		return roleRep.save(role);
	}

	/**
	 * Trouve un utilisateur par son nom d'utilisateur.
	 * 
	 * @param username Le nom d'utilisateur.
	 * @return L'utilisateur trouvé.
	 */
	@Override
	public User findUserByUsername(String username) {
		return userRep.findByUsername(username);
	}

	/**
	 * Récupère un utilisateur par son identifiant.
	 * 
	 * @param id L'identifiant de l'utilisateur.
	 * @return L'utilisateur récupéré.
	 */
	@Override
	public User getUser(Long id) {
		return userRep.findById(id).get();
	}

	/**
	 * Récupère tous les utilisateurs.
	 * 
	 * @return Une liste de tous les utilisateurs.
	 */
	@Override
	public List<User> getAllUsers() {
		return userRep.findAll().stream().collect(Collectors.toList());
	}

	/**
	 * Récupère tous les rôles.
	 * 
	 * @return Une liste de tous les rôles.
	 */
	@Override
	public List<Role> getAllRoles() {
		return rolerepository.findAll().stream().collect(Collectors.toList());
	}

	/**
	 * Enregistre un nouvel utilisateur en vérifiant  son email.
	 * 
	 * @param request La requête d'enregistrement contenant les informations de
	 *                l'utilisateur.
	 * @return L'utilisateur enregistré.
	 * @throws EmailAlreadyExistsException si l'email existe déjà.
	 */
	@Override
	@Transactional
	public User registerUser(RegistrationRequest request) {
		Optional<User> optionalUser = userRep.findByEmail(request.getEmail());
		if (optionalUser.isPresent())
			throw new EmailAlreadyExistsException("Email déjà existant!");
		 User username = userRep.findByUsername(request.getUsername());
		    if (username != null) {
		        throw new UsernameAlreadyExistsException("Nom d'utilisateur déjà existant!");
		    }
		User newUser = new User();
		newUser.setUsername(request.getUsername());
		newUser.setEmail(request.getEmail());

		newUser.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
		newUser.setEnabled(false);

		userRep.save(newUser);

		
		List<Role> roles = new ArrayList<>();
		
		newUser.setRoles(roles);

		String code = this.generateCode();

		VerificationToken token = new VerificationToken(code, newUser);
		verificationTokenRepo.save(token);

		sendEmailUser(newUser, token.getToken());

		return userRep.save(newUser);
	}
	
    /**
     * Méthode pour envoyer un code de réinitialisation de mot de passe à l'adresse e-mail spécifiée.
     *
     * @param email l'adresse e-mail à laquelle envoyer le code de réinitialisation
     */
	@Transactional
    public void sendResetPasswordCode(String email) {
        Optional<User> optionalUser = userRep.findByEmail(email);
        if (!optionalUser.isPresent()) {
            throw new EmailNotFoundException("Adresse e-mail introuvable!");
        }
        
        User user = optionalUser.get();

        String code = generateCode();
        VerificationToken token = new VerificationToken(code, user);
        verificationTokenRepo.save(token);

        sendEmailUser(user, code);
    }
	 /**
     * Méthode pour réinitialiser le mot de passe de l'utilisateur.
     *
     * @param email l'adresse e-mail de l'utilisateur
     * @param code le code de réinitialisation du mot de passe
     * @param newPassword le nouveau mot de passe
     */
	@Transactional
	public void resetPassword(String email, String code, String newPassword) {
	    Optional<User> optionalUser = userRep.findByEmail(email);
	    if (!optionalUser.isPresent()) {
	        throw new EmailNotFoundException("Adresse e-mail introuvable!");
	    }
	    
	    User user = optionalUser.get();

	    VerificationToken token = verificationTokenRepo.findByToken(code);
	    if (token == null || !token.getUser().getEmail().equals(email)) {
	        throw new InvalidTokenException("Invalid Token");
	    }

	    Calendar calendar = Calendar.getInstance();

	    if ((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
	       
	        throw new ExpiredTokenException("Expired Token");
	    }
	  

	    user.setPassword(bCryptPasswordEncoder.encode(newPassword)); // Mettre à jour le mot de passe
	    userRep.save(user);
	    verificationTokenRepo.delete(token);
	    
	}


	  
	

	/**
	 * Génère un code aléatoire.
	 * 
	 * @return Le code généré.
	 */
	@Transactional
	private String generateCode() {
		Random random = new Random();
		Integer code = 100000 + random.nextInt(900000);

		return code.toString();
	}

	/**
	 * Envoie un email à un utilisateur.
	 * 
	 * @param u    L'utilisateur à qui envoyer l'email.
	 * @param code Le code à inclure dans l'email.
	 */
	@Override
	@Transactional
	public void sendEmailUser(User u, String code) {
	
		String emailBody = "<html>" +
                "<head>" +
                "<style>" +
                "h1 { color: #333; }" +
                "p { font-size: 16px; line-height: 1.5; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<p>Bonjour <strong>" + u.getUsername() + "</strong>,</p>" +
                "<p>Votre code de validation est <strong>" + code + "</strong>.</p>" +
                "</body>" +
                "</html>";

		emailSender.sendEmail(u.getEmail(), emailBody);
	}

	/**
	 * Valide un token.
	 * 
	 * @param code Le token à valider.
	 * @return L'utilisateur associé au token.
	 * @throws InvalidTokenException si le token est invalide.
	 * @throws ExpiredTokenException si le token a expiré.
	 */
	@Override
	public User validateToken(String code) {
		VerificationToken token = verificationTokenRepo.findByToken(code);

		if (token == null) {
			throw new InvalidTokenException("Invalid Token");
		}

		User user = token.getUser();

		Calendar calendar = Calendar.getInstance();

		if ((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
			
			throw new ExpiredTokenException("expired Token");
		}
		verificationTokenRepo.delete(token);
		user.setEnabled(true);
		userRep.save(user);
		return user;
	}
}
