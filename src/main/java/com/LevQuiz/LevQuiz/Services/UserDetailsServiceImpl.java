package com.LevQuiz.LevQuiz.Services;

import com.LevQuiz.LevQuiz.Models.AppUser;
import com.LevQuiz.LevQuiz.Models.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

// Cette classe va implementé de l'interface UserDetailsService de Spring
@AllArgsConstructor // pour l'injection des dépendances
public class UserDetailsServiceImpl implements UserDetailsService {
    // Cette interface possède une seule méthode à implémenter
    // C'est cette méthode qui est appélé à chaque fois que l'utilisateur essaye de s'authentifier avec son Username et password

    // Injections notre serviceAccount
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // cherchons l'utilisateur ou le getter
        AppUser appUser = accountService.findByUsername(username);
        // Vérifier si l'utilisateur existe dans la base de donnée
        if ( appUser == null){
            // si l'utilisateur n'existe pas
            throw  new UsernameNotFoundException("Nom d'utilisateur :" + username + "N'existe pas !");
        }
        // Sinon s'il existe
        // Envoi l'utilisateur à Spring Security
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        // Cherchons tous les roles que l'utilisateur possède
        Set<UserRole> userRoles = appUser.getUserRoles();
        // Spring Security ne connaît pas userRole, mais il connaît authorities // donc ajoutons userRole à authorities
        ((Set) userRoles).forEach(userRole->{
            authorities.add(new SimpleGrantedAuthority(userRoles.toString())); // on convertie les roles en string
        });
        // on retourne l'utilisateur avec tous les roles
        // new User // User de Spring pour qu'il connaisse l'utilisateur pour l'authentifier
        return new User(appUser.getUsername(), appUser.getPassword(), authorities);
    }
}
