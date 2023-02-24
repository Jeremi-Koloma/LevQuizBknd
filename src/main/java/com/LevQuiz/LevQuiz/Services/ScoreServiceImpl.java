package com.LevQuiz.LevQuiz.Services;

import com.LevQuiz.LevQuiz.Models.AppUser;
import com.LevQuiz.LevQuiz.Models.Quiz;
import com.LevQuiz.LevQuiz.Models.Scores;
import com.LevQuiz.LevQuiz.Repositories.AppUserRepository;
import com.LevQuiz.LevQuiz.Repositories.QuizRepository;
import com.LevQuiz.LevQuiz.Repositories.ScoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ScoreServiceImpl implements ScoreService{
    private ScoreRepository scoreRepository;
    private final AppUserRepository appUserRepository;
    private final QuizRepository quizRepository;

    @Override
    public Scores saveScore(Long scores, Long userid, Long quizid) {
        // Recupérons les id dans les repository
        AppUser appUser = appUserRepository.findUserById(userid);
        Quiz quiz = quizRepository.findQuizById(quizid);

        Scores scores1 = new Scores();
        scores1.setScore(scores);
        scores1.setUsername(appUser.getUsername());
        scores1.setQuiztire(quiz.getTitre());
        if (scores1.getScoreDate() == null){
            scores1.setScoreDate(new Date());
        }
        scores1.setAppUser(appUser);
        scores1.setQuiz(quiz);

        return scoreRepository.save(scores1);
    }


    @Override
    public List<Object> findUserScores(Long userid) {
        return scoreRepository.Userscores(userid);
    }

    @Override
    public List<Object> getUsersScoreByQuiz(Long idquiz) {
        return scoreRepository.getUserScoresByQuiz(idquiz);
    }
}
