package com.LevQuiz.LevQuiz.Services;

import com.LevQuiz.LevQuiz.Models.AppUser;
import com.LevQuiz.LevQuiz.Models.Quiz;
import com.LevQuiz.LevQuiz.Repositories.QuizRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

// Cette classe va implémenté l'interface ServiceQuiz
@Service // Pour dire qu'il sagit du service logique
@Transactional
@AllArgsConstructor // Pour l'injections des dépendances notre QuizRepository
public class QuizServiceImpl implements QuizService{
    // Implementons les méthodes
    // Injectons le RepositoryQuiz
    private QuizRepository quizRepository;

    @Override
    public Quiz saveQuiz(AppUser appUser, HashMap<String, String> request, String titre) {
        Quiz quiz = new Quiz();
        quiz.setUsername(appUser.getUsername());
        quiz.setQuizDate(new Date());
        quizRepository.save(quiz);
        return quiz;
    }

    @Override // méthode pour Afficher tous Quiz
    public List<Quiz> quizList() {
        return quizRepository.findAll();
    }

    @Override
    public Quiz getQuizById(Long id) {
        return quizRepository.findQuizById(id);
    }

    @Override
    public List<Quiz> findQuizByUsername(String username) {
        return quizRepository.findByUsername(username);
    }

    @Override
    public Quiz deleteQuiz(Quiz quiz) {
        quizRepository.findQuizById(quiz.getId());
        return quiz;
    }

}
