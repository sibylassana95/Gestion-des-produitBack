package com.siby.produits.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siby.produits.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


/**
 * Classe de filtre d'authentification JWT qui étend le filtre d'authentification par nom d'utilisateur et mot de passe de Spring Security.
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

    /**
     * Constructeur du filtre d'authentification JWT
     *
     * @param authenticationManager le gestionnaire d'authentification Spring Security
     */
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		super();
		this.authenticationManager = authenticationManager;
	}

    /**
     * Tente d'authentifier la demande
     *
     * @param request la requête HTTP
     * @param response la réponse HTTP
     * @return l'authentification
     * @throws AuthenticationException si l'authentification échoue
     */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		User user = null;
		try {
			user = new ObjectMapper().readValue(request.getInputStream(), User.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
	}

    /**
     * Gère l'authentification réussie
     *
     * @param request la requête HTTP
     * @param response la réponse HTTP
     * @param chain la chaîne de filtres
     * @param authResult le résultat de l'authentification
     * @throws IOException si une erreur d'E/S se produit
     * @throws ServletException si une erreur de servlet se produit
     */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		org.springframework.security.core.userdetails.User springUser = (org.springframework.security.core.userdetails.User) authResult
				.getPrincipal();

		List<String> roles = new ArrayList<>();
		springUser.getAuthorities().forEach(au -> {
			roles.add(au.getAuthority());
		});

		String jwt = JWT.create().withSubject(springUser.getUsername())
				.withArrayClaim("roles", roles.toArray(new String[roles.size()]))
				.withExpiresAt(new Date(System.currentTimeMillis() + SecParams.EXP_TIME))
				.sign(Algorithm.HMAC256(SecParams.SECRET));

		response.addHeader("Authorization", jwt);

	}

    /**
     * Gère l'authentification échouée
     *
     * @param request la requête HTTP
     * @param response la réponse HTTP
     * @param failed l'exception d'authentification échouée
     * @throws IOException si une erreur d'E/S se produit
     * @throws ServletException si une erreur de servlet se produit
     */
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		if (failed instanceof DisabledException) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.setContentType("application/json");
			Map<String, Object> data = new HashMap<>();

			data.put("errorCause", "disabled");
			data.put("message", "L'utilisateur est désactivé !");
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(data);
			PrintWriter writer = response.getWriter();
			writer.println(json);
			writer.flush();

		} else {
			super.unsuccessfulAuthentication(request, response, failed);
		}
	}

}
