package com.LevQuiz.LevQuiz.SpringConfig;

import com.LevQuiz.LevQuiz.Models.AppUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Cette classe va étendre de UsernamePasswordAuthenticationFilter pour la Génération du Token
 // pour l'injection des dépendances
public class JwtAuthentication extends UsernamePasswordAuthenticationFilter {
    // Cette classe à deux méthodes à utiliser

    // Ces deux filtres ont besoin de AuthenticationManager pour fonctionner
    private AuthenticationManager authenticationManager;

    //
    public JwtAuthentication(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

    // Cette classe à deux méthodes à utiliser

    // La prémière méthode quand l'utilisateur essaye de s'authentifier, c'est cette méthode qui est appelé par Spring Security
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("------- Essaie d'Authentification -----");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        AppUser appUser;
        try {
            appUser = objectMapper.readValue(request.getInputStream(), AppUser.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to convert Json into Java Object: " + e);
        }
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                appUser.getUsername(),
                appUser.getPassword()));
    }


    // La deuxième méthode quand l'authentification a reussi
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        // Recuperons l'utilisateur qui s'est connecter
        // User de Spring
        System.out.println("------- Connexion Reussi -----");
        User user = (User) authentication.getPrincipal();
        // Créons une liste de roles que utilisateur possède
        List<String> roles = new ArrayList<>();
        user.getAuthorities().forEach(authority->{
            roles.add(authority.getAuthority());
        });

        // Création du Token
        String jwtToken = JWT.create()
                .withIssuer("levQuiz.com") // Le nom de l'application qui a généré le token
                .withSubject(user.getUsername()) // le nom d'utilisateur de la personne
                .withArrayClaim("roles",roles.stream().toArray(String[]::new)) // liste des roles de l'utilisateur
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME)) // date d'expiration
                .sign(Algorithm.HMAC256(SecurityConstants.SECRET)); // L'Algorithme de Cryptage
        response.addHeader(SecurityConstants.HEADER_TYPE, SecurityConstants.TOKEN_PREFIX + jwtToken);

    }
}
