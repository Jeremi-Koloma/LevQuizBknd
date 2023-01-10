package com.LevQuiz.LevQuiz.Services;

import com.LevQuiz.LevQuiz.Models.AppUser;
import com.LevQuiz.LevQuiz.Models.Quiz;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

public interface QuizService {
    // Une méthode pour Enregister le Quiz les paramètre :
    // AppUser : Pour connaître l'utilisateur qui a créer le Quiz
    // titre : Le Titre du quiz
    Quiz saveQuiz(AppUser appUser, HashMap<String, String> request);

    // Une méthode qui va retourné une liste de Quiz
    List<Quiz> quizList();

    // Une méthode qui va retouné un quiz par son ID
    Quiz getQuizById(Long id);

    // Une méthode pour retouner une liste de Quiz créer par un utilisateur
    List<Quiz> findQuizByUsername(String username );

    // Une méthode qui va retourné un Quiz pour le supprimer.
    Quiz deleteQuiz(Quiz quiz);




}
