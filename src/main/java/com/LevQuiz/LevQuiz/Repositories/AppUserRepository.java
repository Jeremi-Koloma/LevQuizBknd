package com.LevQuiz.LevQuiz.Repositories;

import com.LevQuiz.LevQuiz.Models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Cette classe va étendre de l'interface JpaRepository pour avoir accès à la base de donnée
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    //
    AppUser findByUsername(String username);

    //
    AppUser findByEmail(String email);

    //
    AppUser findUserById(Long id);

    // Retourner une liste des utilisateurs pour un champs de recherche l'utilisateur
    public List<AppUser> findByUsernameContaining(String username);

}
