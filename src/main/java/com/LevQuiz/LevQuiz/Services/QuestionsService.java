package com.LevQuiz.LevQuiz.Services;

import com.LevQuiz.LevQuiz.Models.Questions;

import java.util.List;

public interface QuestionsService {
    // Définissons nos méthodes

    // une méthode qui va retourner une question pour le Créer une Question à un Quiz
    Questions saveQuestions(Questions questions, Long idQuiz);

    // Une méthode qui va retourner une liste des Questions
    List<Questions> listQuestions();

    // Une méthode qui va retourner une Question pour Afficher une seule Question
    Questions findQuestionById(Long id);

    // Une méthode pour modifier une Question
    Questions updateQuestion(Long id, Questions questions);

    // une méthode pour supprimer une Question
    void deleteQuestionById(Long id);

}
