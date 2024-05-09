package com.siby.produits.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

/**
 * Classe de configuration de sécurité.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	 private static final String[] WHITE_LIST_URL = {
			 "/login", 
			 "/register/**", 
			 "/verifyEmail/**",
			 "/sendmail",
			 "/reset-password",
	            "/v2/api-docs",
	            "/v3/api-docs",
	            "/v3/api-docs/**",
	            "/swagger-resources",
	            "/swagger-resources/**",
	            "/configuration/ui",
	            "/configuration/security",
	            "/swagger-ui/**",
	            "/webjars/**",
	            "/swagger-ui.html"};
	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	AuthenticationManager authMgr;
     /**
	 * Configuration de l'AuthenticationManager.
	 * @param http Le gestionnaire de sécurité HTTP.
	 * @param bCryptPasswordEncoder Le crypteur de mot de passe.
	 * @param userDetailsService Le service de détails de l'utilisateur.
	 * @return L'AuthenticationManager configuré.
	 * @throws Exception Si une erreur survient lors de la configuration.
	 */
	@Bean
	public AuthenticationManager authManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder,
			UserDetailsService userDetailsService) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userDetailsService)
				.passwordEncoder(bCryptPasswordEncoder).and().build();
	}
	
   
   /**
	 * Configuration de la chaîne de filtres de sécurité.
	 * @param http Le gestionnaire de sécurité HTTP.
	 * @return La chaîne de filtres de sécurité configurée.
	 * @throws Exception Si une erreur survient lors de la configuration.
	 */
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.csrf(csrf -> csrf.disable())

				.cors(cors -> cors.configurationSource(new CorsConfigurationSource() {
					@Override
					public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
						CorsConfiguration cors = new CorsConfiguration();
						cors.setAllowedOrigins(Arrays.asList("*"));
						cors.setAllowedOrigins(Collections.singletonList("*"));
						cors.setAllowedMethods(Collections.singletonList("*"));
						cors.setAllowedHeaders(Collections.singletonList("*"));

						cors.setExposedHeaders(Collections.singletonList("Authorization"));

						return cors;
					}
				}))

				.authorizeHttpRequests(requests -> requests
						/**
						 * Les Endpoint Produits
						 */
						.requestMatchers("/produits/all/**").hasAnyAuthority("ADMIN", "USER")
						.requestMatchers("/produits/getbyid/**").hasAnyAuthority("ADMIN", "USER")
						.requestMatchers("/produits/prodscat/**").hasAnyAuthority("ADMIN", "USER")
						.requestMatchers(HttpMethod.POST, "/produits/addprod/**").hasAnyAuthority("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/produits/updateprod/**").hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/produits/delprod/**").hasAuthority("ADMIN")
						/**
						 * Les Endpoint  Categorie
						 */
						.requestMatchers("/categorie/all/**").hasAnyAuthority("ADMIN", "USER")
						.requestMatchers("/categorie/getbyid/**").hasAnyAuthority("ADMIN", "USER")
						.requestMatchers(HttpMethod.POST, "/categorie/add/**").hasAnyAuthority("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/categorie/update/**").hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/categorie/del/**").hasAuthority("ADMIN")
						
						/**
						 * Les Endpoint  Images
						 */
						.requestMatchers("/image/get/info/**").hasAnyAuthority("ADMIN", "USER")
						.requestMatchers("/image/load/**").hasAnyAuthority("ADMIN", "USER")
						.requestMatchers(HttpMethod.POST, "/image/upload/**").hasAnyAuthority("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/image/update/**").hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/image/del/**").hasAuthority("ADMIN")
						/**
						 * Les Endpoint  Users
						 */
						.requestMatchers("/users/getbyid/**").hasAnyAuthority("ADMIN", "USER")
						.requestMatchers(HttpMethod.PUT, "/users/update/**").hasAnyAuthority("ADMIN","USER")
						.requestMatchers(HttpMethod.POST, "/users/add/**").hasAuthority("ADMIN")
						.requestMatchers("/users/all/**").hasAnyAuthority("ADMIN", "USER")
						.requestMatchers("/role/all/**").hasAnyAuthority("ADMIN", "USER")
						.requestMatchers(HttpMethod.POST,"/role/add/**").hasAnyAuthority("ADMIN", "USER")
						.requestMatchers(WHITE_LIST_URL).permitAll()

						.anyRequest().authenticated())
				.addFilterBefore(new JWTAuthenticationFilter(authMgr), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

}
