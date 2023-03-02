package com.LevQuiz.LevQuiz.Services;

import com.LevQuiz.LevQuiz.Models.Scores;

import java.util.List;

public interface ScoreService {

    // Une méthode pour enregistrer les socres
    Scores saveScore(Long scores, Long correctanswer,Long incorrectanswer, Long totalquestions, Long userid, Long quizid);

    List<Object> findUserScores(Long userid);


    List<Object> getUsersScoreByQuiz(Long idquiz);

}
