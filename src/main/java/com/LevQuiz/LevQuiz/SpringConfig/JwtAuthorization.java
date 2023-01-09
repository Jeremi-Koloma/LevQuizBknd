package com.LevQuiz.LevQuiz.SpringConfig;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// Cette classe va étendre de OncePerRequestFilter
public class JwtAuthorization extends OncePerRequestFilter {
    // cette classe possède une méthode, cette méthode s'exécute à chaque requête qui arrive avec le token
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Permèttrer les routes ou les Domaine pour la bonne comminication avec le Frontend
        response.addHeader("Access-Control-Allow-Origin", SecurityConstants.CLIENT_DOMAIN_URL);
        response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, "
                + "Content-Type, Access-Control-Request-Method, " + "Access-Control-Request-Headers, Authorization");

        response.addHeader("Access-Control-Expose-Headers",
                "Access-Control-Allow-Origin, " + "Access-Control-Allow-Credentials, " + "Authorization");

        response.addHeader("Access-Control-Allow-Methods", "GET," + "POST, " + "DELETE, " + "POST");

        //Vérifier si
        if (request.getMethod().equalsIgnoreCase("OPTIONS")){
            //
            try {
                response.setStatus(HttpServletResponse.SC_OK);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            // Recuperons le Token dans la requête
            String jwtToken = request.getHeader(SecurityConstants.HEADER_TYPE);
            // vérifier si le token n'est pas vide et si ça contient le "Bearer"
            if (jwtToken == null || !jwtToken.startsWith(SecurityConstants.TOKEN_PREFIX)){
                // si c'est bon, alors il passe
                filterChain.doFilter(request, response);
                return;
            }
            // Décoder le token pour extraire les informations sur l'utilisateur
            JWT.require(Algorithm.HMAC256(SecurityConstants.SECRET)); // la même clé secrete
            DecodedJWT jwt = JWT.decode(jwtToken.substring(SecurityConstants.TOKEN_PREFIX.length())); // supprimer le Bearer
            // Quand le token est décodé, Extraire maintenant les données
            String username = jwt.getSubject(); // recupération du username de l'utilisateur
            List<String> roles = jwt.getClaims().get("roles").asList(String.class); // recupération des roles
            // Convertir la liste en une liste de String
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            roles.forEach(role->authorities.add(new SimpleGrantedAuthority(role))); // conversion des roles en string

            UsernamePasswordAuthenticationToken authenticatedUser = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
            filterChain.doFilter(request, response);
        }




    }
}
