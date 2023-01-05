package com.LevQuiz.LevQuiz.Repositories;

import com.LevQuiz.LevQuiz.Models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// Cette classe va étendre de JpaRepository avec Entity nama, ID pour avoir accès à la base de donnée
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    /*// Une méthode pour avoir tous les Quiz
    @Query("SELECT q FROM quiz q oder by q.quiz_date DESC") // oder by : le plus récent Quiz sera au top des Quiz
    List<Quiz> findAll();

    // Avoir un Quiz par le nom d'un utilisateur
    @Query("SELECT q FROM quiz q WHERE q.username=:username oder by q.quiz_date DESC") // oder by : le plus récent Quiz sera au top des Quiz
    List<Quiz> findByUsername(@Param("username") String username);

    // Retourner un Quiz par son ID
    @Query("SELECT q FROM quiz q WHERE q.id=:x")
    Quiz findQuizById(@Param("x") Long id);

    // Suppression de Quiz
    @Query("SELECT quiz WHERE id=:x")
    void deleteQuizById(@Param("x") Long id);*/

}
