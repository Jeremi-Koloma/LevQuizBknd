package com.LevQuiz.LevQuiz.Repositories;

import com.LevQuiz.LevQuiz.Models.Reponses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Cette classe va étendre de JpaRepository avec EntityName et ID en param pour l'enregister dans la base de donnée
@Repository
public interface ReponsesRepository extends JpaRepository<Reponses, Long> {
    // une méthode qui va retourné un String pour supprimer une Reponse par son Id
    String findReponseById(Long id);

    // Une méthode qui va permet de verifier si une question existe déja
    boolean existsByReponse(String reponse);
}
