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
public class ReponseServiceImpl implements ReponseService {
    // implementons les méthode

    // injectons le repository Reponse pour la persistance des données
    private ReponsesRepository reponsesRepository;

    // injectons le repository Questions pour pouvoir retourner une Question par son id
    private QuestionsRepository questionsRepository;


    @Override // implementation de la methode qui va crée la reponse
    public Reponses saveReponse(Reponses reponses, Long idquestion) {
        // créeons une variable pour stocker le question qui se trouve dans RepositoryQuestion
        Questions questions = questionsRepository.findQuestionsById(idquestion);
        // Maitenant affectons la Reponse à la Question
        reponses.setQuestions(questions);
        // Enregister la reponse
        return reponsesRepository.save(reponses);
    }

    @Override // implementation de la méthode qui va lister les reponses
    public List<Reponses> listReponse() {
        return reponsesRepository.findAll();
    }

    @Override // implementation de la méthode qui va afficher une seule reponse
    public Reponses findReponseById(Long id) {
        return reponsesRepository.findById(id).get();
    }

    @Override // implementation de la méthode pour modifier une reponse
    public Reponses updateReponse(Long id, Reponses reponses) {
        return reponsesRepository.findById(id) // Si on trouve l'ID on fait du mappage
                .map(repons->{
                    repons.setReponse(reponses.getReponse());
                    // Maintenant après avoir modifier on l'enregistre
                    return reponsesRepository.save(repons);
                }).orElseThrow(()->new RuntimeException("Reponse non Trouvé"));
    }

    @Override // implementation de la méthode qui va supprimer une reponse
    public void deleteReponseById(Long id) {
        reponsesRepository.deleteById(id);
    }
}
