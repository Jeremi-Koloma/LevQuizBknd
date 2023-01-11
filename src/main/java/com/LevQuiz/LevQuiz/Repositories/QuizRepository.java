package com.LevQuiz.LevQuiz.Repositories;

import com.LevQuiz.LevQuiz.Models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// Cette classe va étendre de JpaRepository avec Quiz en param et ID pour enregister dans la base de donnée
@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    // Une méthode qui va retourné une liste de Quiz par odre recent
    @Query(value = "SELECT * FROM quiz ORDER BY quiz_date DESC;", nativeQuery = true)
    List<Quiz> findAll();
    // UNE METHODE POUR RECUPERER UN QUIZ PAR LE TITRE.
    Quiz findQuizByTitre(String titre);

    // Une méthode qui va retourné une liste de Quiz créer par d'un utilisateur par ordre recent
    @Query(value = "SELECT * FROM quiz WHERE username=:username ORDER BY quiz_date DESC;", nativeQuery = true)
    List<Quiz> findByUsername(@Param("username") String username);

    // Une méthode qui va retourné un Quiz par son ID
    @Query(value = "SELECT * FROM quiz WHERE id=:x", nativeQuery = true)
    Quiz findQuizById(@Param("x") Long id);

    // Une méthode pour suppression de Quiz
    //@Modifying
    //@Query(value = "DELETE quiz WHERE id=:x", nativeQuery = true)
    //void deleteQuizById(@Param("x") Long id);


}
