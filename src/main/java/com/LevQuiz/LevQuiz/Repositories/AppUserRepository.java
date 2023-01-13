package com.LevQuiz.LevQuiz.Repositories;

import com.LevQuiz.LevQuiz.Models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// Cette classe va étendre de l'interface JpaRepository pour avoir accès à la base de donnée
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    // Une méthode pour trouver un utilisateur par son Username
    AppUser findByUsername(String username);

    // Une méthode qui va permettre de retrouver un utilisateur par son email.
    AppUser findByEmail(String email);

    // une méthode qui va permettre de retourner un utilisateur par son id
    AppUser findUserById(Long id);

    // Retourner une liste des utilisateurs pour un champs de recherche l'utilisateur
    List<AppUser> findByUsernameContaining(String username);

}
