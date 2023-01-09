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
    //
    AppUser findByUsername(String username);

    //
    AppUser findByEmail(String email);

    //
    @Query(value = "SELECT appuser FROM appuser appuser WHERE appuser.id=:x", nativeQuery = true)
    AppUser findUserById(@Param("x") Long id);

    // Retourner une liste des utilisateurs pour un champs de recherche l'utilisateur
    List<AppUser> findByUsernameContaining(String username);

}
