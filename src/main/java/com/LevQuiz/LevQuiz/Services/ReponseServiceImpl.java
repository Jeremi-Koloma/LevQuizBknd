package com.LevQuiz.LevQuiz.Services;

import com.LevQuiz.LevQuiz.Models.Questions;
import com.LevQuiz.LevQuiz.Models.Reponses;
import com.LevQuiz.LevQuiz.Repositories.QuestionsRepository;
import com.LevQuiz.LevQuiz.Repositories.ReponsesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Identifier cette classe comme étant du code métier (Services);
@AllArgsConstructor // pour l'injection des dependances
// Cette classe va implementé notre interface serviceReponse
public class ReponseServiceImpl implements ReponseService{
    // implementons les méthode

    // injectons le repository Reponse pour la persistance des données
    private ReponsesRepository reponsesRepository;

    // injectons le repository Questions pour pouvoir retourner une Question par son id
    private QuestionsRepository questionsRepository;


    @Override // implementation de la methode qui va crée la reponse
    public Reponses saveReponse(Reponses reponses, Long idquestion) {
        // créeons une variable pour stocker le question qui se trouve dans RepositoryQuestion
        Questions questions = questionsRepository.findQuestionsById(idquestion);
        // Maitenant affectons la Question à la reponse
        reponses.setQuestions(questions);
        // Enregister la reponse
        return reponsesRepository.save(reponses);
    }

    @Override
    public List<Reponses> listReponse() {
        return null;
    }

    @Override
    public Reponses findByReponse(Long id) {
        return null;
    }

    @Override
    public Reponses updateReponse(Long id, Reponses reponses) {
        return null;
    }

    @Override
    public String deleteReponse(Long id) {
        return null;
    }
}
