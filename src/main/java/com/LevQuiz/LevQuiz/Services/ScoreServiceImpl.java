package com.LevQuiz.LevQuiz.Services;

import com.LevQuiz.LevQuiz.Models.AppUser;
import com.LevQuiz.LevQuiz.Models.Quiz;
import com.LevQuiz.LevQuiz.Models.Scores;
import com.LevQuiz.LevQuiz.Repositories.AppUserRepository;
import com.LevQuiz.LevQuiz.Repositories.QuizRepository;
import com.LevQuiz.LevQuiz.Repositories.ScoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ScoreServiceImpl implements ScoreService{
    private ScoreRepository scoreRepository;
    private final AppUserRepository appUserRepository;
    private final QuizRepository quizRepository;

    @Override
    public Scores saveScore(Scores scores, Long userid, Long quizid) {
        return scoreRepository.save(scores);
    }
}
