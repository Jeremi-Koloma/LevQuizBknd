package com.LevQuiz.LevQuiz.Controllers;

import com.LevQuiz.LevQuiz.Models.Questions;
import com.LevQuiz.LevQuiz.Models.Reponses;
import com.LevQuiz.LevQuiz.Repositories.QuestionsRepository;
import com.LevQuiz.LevQuiz.Services.ReponseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Identifier cette classe comme étant un Controller;
@RequestMapping(value = "/reponses")  // Le path, ou le lien pour le Navigateur
@AllArgsConstructor // pour l'injection des dépendances
public class ReponsesRessource {
    // Injectons notre interface serviceReponse
    private ReponseService reponseService;

    // Injectons notre repositoryQuestion pour avoir accès aux Question
    private QuestionsRepository questionsRepository;

    // une méthode qui va créer une reponse
    @PostMapping("/create/{idquestion}")
    public ResponseEntity<?> createResponse(@RequestBody Reponses reponses, @PathVariable Long idquestion){
        // Recuperons la question dans son repository
        Questions idQuest = questionsRepository.findQuestionsById(idquestion);
        // Vérifions si la question existe
        if (idQuest == null){
            // Si la Question est null, n'existe pas
            return  new ResponseEntity<>("Question non trouver !", HttpStatus.NOT_FOUND);
        }
        // sinon si la question existe, essayons d'enregister la Reponse
        try {
            Reponses reponses1 = reponseService.saveReponse(reponses, idQuest.getId());
            return new ResponseEntity<>(reponses1,HttpStatus.OK);
        }catch (Exception e){
            return  new ResponseEntity<>("Une erreur s'est produit lors de la creation de reponse !", HttpStatus.BAD_REQUEST);
        }
    }


    // Une méthode qui va lister toutes les réponses




}
