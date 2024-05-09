package com.siby.produits.security;

import com.siby.produits.model.User;
import com.siby.produits.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



/**
 * Classe de service d'informations d'utilisateur qui implémente l'interface UserDetailsService de Spring Security.
 */
@Service
public class MyUserDetailsService implements UserDetailsService {
    /**
     * Le service d'application injecté.
     */
	@Autowired
	UserService userService;

    /**
     * Charge les détails de l'utilisateur par son nom d'utilisateur.
     *
     * @param username le nom d'utilisateur
     * @return les détails de l'utilisateur
     * @throws UsernameNotFoundException si l'utilisateur n'est pas trouvé
     */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findUserByUsername(username);

		if (user == null)
			throw new UsernameNotFoundException("Utilisateur introuvable !");

		List<GrantedAuthority> auths = new ArrayList<>();

		user.getRoles().forEach(role -> {
			GrantedAuthority auhority = new SimpleGrantedAuthority(role.getRole());
			auths.add(auhority);
		});

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				user.getEnabled(), true, true, true, auths);
	}
}
