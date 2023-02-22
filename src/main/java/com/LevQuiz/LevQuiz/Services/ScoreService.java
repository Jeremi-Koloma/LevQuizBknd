package com.LevQuiz.LevQuiz.Services;

import com.LevQuiz.LevQuiz.Models.Scores;

public interface ScoreService {
    Scores saveScore(Scores scores, Long userid, Long quizid);
}
