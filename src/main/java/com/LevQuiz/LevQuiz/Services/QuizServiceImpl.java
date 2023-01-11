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
    public Quiz saveQuiz(AppUser appUser, HashMap<String, String> request) {
        Quiz quiz = new Quiz();
        String description = request.get("description");
        String titre = request.get("titre");
        quiz.setUsername(appUser.getUsername());
        quiz.setDescription(description);
        quiz.setTitre(titre);
        quiz.setQuizDate(new Date());
        //quiz.setAppUser(appUser); // plus la peine
        quizRepository.save(quiz);
        return quiz;
    }

    @Override // implementation de la méthode pour Afficher tous les Quiz
    public List<Quiz> quizList() {
        return quizRepository.findAll();
    }

    @Override // implementation de la méthode qui va afficher un seul Quiz
    public Quiz getQuizById(Long id) {
        return quizRepository.findQuizById(id);
    }

    @Override // implementation de la méthode qui va Afficher tous les Quiz créer par un l'utilisateur
    public List<Quiz> findQuizByUsername(String username) {
        return quizRepository.findByUsername(username);
    }

    @Override // implementation de la méthode qui va supprimer un Quiz
    public void deleteQuizById(Long id) {
        quizRepository.deleteById(id);
    }

}
