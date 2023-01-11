package com.LevQuiz.LevQuiz.Controllers;

import com.LevQuiz.LevQuiz.Models.Questions;
import com.LevQuiz.LevQuiz.Models.Quiz;
import com.LevQuiz.LevQuiz.Repositories.QuestionsRepository;
import com.LevQuiz.LevQuiz.Repositories.QuizRepository;
import com.LevQuiz.LevQuiz.Repositories.ReponsesRepository;
import com.LevQuiz.LevQuiz.Services.QuestionsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Identifer la classe comme un controller
@RequestMapping(value = "/Questions")
@AllArgsConstructor // pour l'injections des dépendances
public class QuestionsRessource {
    // Injectons le serviceQuestion pour bénéficier des méthodes
    private QuestionsService questionsService;

    // Injectons le repositoryQuestion aussi
    private QuestionsRepository questionsRepository;

    // Injectons le repositoryQuiz pour attribuer la Question à un Quiz
    private QuizRepository quizRepository;
    private final ReponsesRepository reponsesRepository;


    // une méthode qui va permettre de créer une question
    @PostMapping("/saveQuestion/{id}")
    public ResponseEntity<?> createQuestion(@RequestBody Questions questions, @PathVariable Long id){
        // Recupérons d'abord le Quiz dans son Repository
        Quiz idQuiz = quizRepository.findQuizById(id);
        // vérifier si le Quiz existe d'abord
        if (idQuiz == null){
            // si le Quiz n'existe pas
            return new ResponseEntity<>("Quiz non trouver !", HttpStatus.NOT_FOUND);
        }
        // sinon si le quiz existe, alors essayons de l'attribuer une Question
        try {
            // on l'attribue la Question
            Questions questions1 = questionsService.saveQuestions(questions, idQuiz.getId());
            return new ResponseEntity<>(questions1, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Une erreur s'est produit lors de la création du Question", HttpStatus.BAD_REQUEST);
        }

    }

    // Une méthode qui va afficher la liste de toutes les Questions
    @GetMapping("/listQuestion")
    public ResponseEntity<?> getListQuestions(){
        // Récupérons la liste des questions
        List<Questions> questionsList = questionsService.listQuestions();
        // vérifions si la liste des questions est vide
        if (questionsList.isEmpty()){
            // si c'est vide
            return new ResponseEntity<>("Aucune Question trouver !", HttpStatus.NOT_FOUND);
        }
        // sinon s'il y'a une liste,
        return  new ResponseEntity<>(questionsList,HttpStatus.OK);
    }

    // une méthode qui va retourner une seule question
    @GetMapping("/getQuestionById/{id}")
    public ResponseEntity<?> getQuestionById(@PathVariable("id") Long id){
        // Recupérons le question
        Questions questions = questionsService.findQuestionById(id);
        // vérifions si la question est null
        if (questions == null){
            return new ResponseEntity<>("Question non trouver !", HttpStatus.NOT_FOUND);
        }
        // sinon si on trouve, on le retourne
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }


    // une méthode qui va permettre de supprimer une question
    @DeleteMapping("/deleteQuestion/{id}")
    public ResponseEntity<?> deleteQuestionById(@PathVariable("id") Long id){
        // Recuperons la questions
        Questions questions = questionsService.findQuestionById(id);
        // vérifions si la question existe
        if (questions == null){
            return new ResponseEntity<>("Question non trouver !", HttpStatus.NOT_FOUND);
        }
        // Sinon si la question existe, on le supprimer
        questionsService.deleteQuestionById(id);
        return new ResponseEntity<>("Question Supprimer !", HttpStatus.OK);
    }


}
