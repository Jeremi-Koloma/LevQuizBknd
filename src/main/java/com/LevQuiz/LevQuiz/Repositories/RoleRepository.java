package com.LevQuiz.LevQuiz.Repositories;

import com.LevQuiz.LevQuiz.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Cette classe va étendre de JpaRepository avec le Role en param, Long pour l'enregister dans la base de donnée
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    // Une méthode pour trouver le role par son nom
    Role findRoleByName(String name);


}
