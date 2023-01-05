package com.LevQuiz.LevQuiz.Repositories;

import com.LevQuiz.LevQuiz.Models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Cette classe va étendre de l'interface JpaRepository pour avoir accès à la base de donnée
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    // pour Authentificatioin
    AppUser findByUsername(String username);

    // Retourner un utilisateur par son Email
    AppUser findByEmail(String email);

    // Retourner un utilisateur par son ID
    AppUser findUserById(Long id);

    // Retourner une liste des utilisateurs pour un champ de recherche l'utilisateur
    List<AppUser> findByUsernameContaining(String username);

}
