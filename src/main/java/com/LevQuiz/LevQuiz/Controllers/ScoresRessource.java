package com.LevQuiz.LevQuiz.Controllers;

import com.LevQuiz.LevQuiz.Models.AppUser;
import com.LevQuiz.LevQuiz.Models.Quiz;
import com.LevQuiz.LevQuiz.Models.Scores;
import com.LevQuiz.LevQuiz.Repositories.AppUserRepository;
import com.LevQuiz.LevQuiz.Services.AccountService;
import com.LevQuiz.LevQuiz.Services.QuizService;
import com.LevQuiz.LevQuiz.Services.ScoreService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Pour dire qu'il s'agit qu'il classe controller
@RequestMapping(value = "/score") // le path ou lien dans l'url
@AllArgsConstructor
public class ScoresRessource {

    private ScoreService scoreService;
    private AccountService accountService;
    private QuizService quizService;
    private AppUserRepository appUserRepository;


    @PostMapping("/save/{scores}/{userid}/{quizid}")
    public ResponseEntity<?> saveScore(@PathVariable Long scores, @PathVariable Long userid, @PathVariable Long quizid){ // RequestBody pour dire que les donnée se trouve dans le corps de la requete

        AppUser appUser = accountService.findUserById(userid);
        Quiz quiz = quizService.getQuizById(quizid);


      if(appUser == null){
          return new ResponseEntity<>("UserNotExit", HttpStatus.NOT_FOUND);
      }

        if(quiz == null){
            return new ResponseEntity<>("QuizNotExit", HttpStatus.NOT_FOUND);
        }

      try {
          scoreService.saveScore(scores, userid, quizid);
          return new ResponseEntity<>("score ok", HttpStatus.OK);
      }
      catch (Exception e){
          return new ResponseEntity<>("Erreur", HttpStatus.BAD_REQUEST);
      }
    }




    //Une méthode pour avoir la liste de tous les quiz créer par un utilisateur
    @GetMapping("/userScores/{userid}")
    public ResponseEntity<?> getUserScores(@PathVariable("userid") Long userid ){
        // Recupérer l'utilsateur par son id
        AppUser user = accountService.findUserById(userid);
        // Vérifier si l'utilisateur existe
        if (user == null){
            // Si l'utlisateur est null, n'existe pas
            return  new ResponseEntity<>("Utilisateur n'existe pas !", HttpStatus.NOT_FOUND);
        }
        // Sinon s'il existe essayons de retourner tous les scores de l'utilsateur
        try {
            // recupérer la liste des scores de l'utilisateur
            List<Object> scoresList = scoreService.findUserScores(userid);
            return  new ResponseEntity<>(scoresList, HttpStatus.OK);
        }
        catch (Exception e){
            // Sinon si ça échoue
            return  new ResponseEntity<>("Une Erreur s'est produit lors de l'Affichage des scores de l'utilisateur", HttpStatus.BAD_REQUEST);
        }
    }





    //Une méthode pour avoir la liste de tous les quiz créer par un utilisateur
    @GetMapping("/userScoresByQuiz/{quizid}")
    public ResponseEntity<?> userScoresByQuiz(@PathVariable("quizid") Long quizid ){
        // Recupérer l'utilsateur par son id
       Quiz quiz = quizService.getQuizById(quizid);
        // Vérifier si l'utilisateur existe
        if (quiz == null){
            // Si l'utlisateur est null, n'existe pas
            return  new ResponseEntity<>("Quiz non trouver !", HttpStatus.NOT_FOUND);
        }
        // Sinon s'il existe essayons de retourner tous les scores de l'utilsateur
        try {
            // recupérer la liste des scores de l'utilisateur
            List<Object> scoresListe = scoreService.getUsersScoreByQuiz(quizid);
            return  new ResponseEntity<>(scoresListe, HttpStatus.OK);
        }
        catch (Exception e){
            // Sinon si ça échoue
            return  new ResponseEntity<>("Une Erreur s'est produit lors de l'Affichage des scores de l'utilisateur", HttpStatus.BAD_REQUEST);
        }
    }



}
