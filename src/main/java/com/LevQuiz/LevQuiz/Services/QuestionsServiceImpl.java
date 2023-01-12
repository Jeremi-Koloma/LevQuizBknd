package com.LevQuiz.LevQuiz.Services;

import com.LevQuiz.LevQuiz.Models.Questions;
import com.LevQuiz.LevQuiz.Models.Quiz;
import com.LevQuiz.LevQuiz.Repositories.QuestionsRepository;
import com.LevQuiz.LevQuiz.Repositories.QuizRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

// Cette classe va étendre de l'interface ServiceQuestion
@Service // pour dire qu'il s'agit d'une classe de logique métier
@AllArgsConstructor // pour l'injection des dépendance
public class QuestionsServiceImpl implements QuestionsService{
    // Implementons nos méthodes dépuis l'interface

    // Injectons notre repositoryQuestion pour la persistance des données dans la base de donnée
    private QuestionsRepository questionsRepository;
    private final QuizRepository quizRepository;

    @Override // implementation de la méthode qui va Ajouter une Question
    public Questions saveQuestions(Questions questions, Long idQuiz) {
        // Récuperons le Quiz pour pourvoir attribuer la Question au Quiz
        Quiz quiz = quizRepository.findQuizById(idQuiz);
        // affectons le Quiz à notre Question
        questions.setQuiz(quiz);
        return questionsRepository.save(questions); // Enregister la question pour la persistance dans la base de donnée
    }

    @Override // implementation de la méthode qui va lister toutes les Questions trouver
    public List<Questions> listQuestions() {
        return questionsRepository.findAll(); // Retourner toutes les questions
    }

    @Override // implementation de la méthode qui va permettre de retourner une seule question
    public Questions findQuestionById(Long id) {
        return questionsRepository.findQuestionsById(id);
    }


    @Override // implementation de la méthode qui va permettre de modifier une quetion
    public Questions updateQuestion(Long id, Questions questions) {
        return questionsRepository.findById(id) // si on trouve l'ID, on fait de mappage
                .map(quest->{
                    quest.setQuestion(questions.getQuestion()); // On le modifier
                    // Après avoir le modifier on l'enregistre
                    return questionsRepository.save(quest);
                }).orElseThrow(()->new RuntimeException("Question non trouver !"));
    }

    @Override // implementation de la méthode qui va permettre de supprimer la Question
    public void deleteQuestionById(Long id) {
        questionsRepository.deleteById(id);
    }
}
