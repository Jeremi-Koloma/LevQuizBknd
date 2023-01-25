package com.LevQuiz.LevQuiz.Repositories;
import com.LevQuiz.LevQuiz.Models.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


// Cette classe va étendre de JpaRepository avec EntityName et ID en param pour l'enregister dans la base de donnée
@Repository
public interface QuestionsRepository extends JpaRepository<Questions, Long> {
    // une méthode pour retouner une question par son id
    Questions findQuestionsById(Long id);

    // Une méthode permettant de vérifier si cette question existe
    boolean existsByQuestion(String question);

}
