package com.LevQuiz.LevQuiz.Controllers;

import com.LevQuiz.LevQuiz.Models.AppUser;
import com.LevQuiz.LevQuiz.Models.Quiz;
import com.LevQuiz.LevQuiz.Services.AccountService;
import com.LevQuiz.LevQuiz.Services.QuizService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;


@RestController // pour identifier la classe comme un controller
@RequestMapping("/quiz") // le lien du path des les url
@AllArgsConstructor // Pour injections des dépendances
@NoArgsConstructor
public class QuizRessource {

    private String quizImageName;
    
    // Injectons l'interface QuizService
    private QuizService quizService;
    //Injectons le AccountService
    private AccountService accountService;

    // Une méthode pour afficher la liste des Quiz
    @GetMapping("/list")
    public ResponseEntity<?> getQuizList(){
        // Récupérons la liste des Quiz
        List<Quiz> quizList = quizService.quizList();
        // Vérifions si la liste de quiz est vide
        if (quizList.isEmpty()){
            // si elle est vide
            return  new ResponseEntity<>("Accun Quiz trouver !", HttpStatus.NOT_FOUND);
        }
        // sinon s'il ya une liste
        return  new ResponseEntity<>(quizList, HttpStatus.OK);

    }


    // Une méthode pour afficher un seul Quiz
    @GetMapping("/getQuizById/{id}")
    public ResponseEntity<?> getOneQuizById(@PathVariable("id") Long id){
        // Recupérons le le quiz
        Quiz quiz = quizService.getQuizById(id);
        // vérifions si le Quiz est nul, n'existe pas
        if (quiz == null){
            // si le Quiz n'existe pas
            return  new ResponseEntity<>("Accun Quiz trouver !", HttpStatus.NOT_FOUND);
        }
        // sinon s'il existe
        return  new ResponseEntity<>(quiz, HttpStatus.OK);
    }


    //Une méthode pour avoir la liste de tous les quiz créer par un utilisateur
    @GetMapping("/getQuizByUsername/{username}")
    public ResponseEntity<?> getQuizByUsername(@PathVariable("username") String username){
        // Recupérer son nom d'utilisateur s'il exite
        AppUser user = getAppUser(username);
        // Vérifier si l'utilisateur existe
        if (user == null){
            // Si l'utlisateur est null, n'existe pas
            return  new ResponseEntity<>("Utilisateur n'existe pas !", HttpStatus.NOT_FOUND);
        }
        // Sinon s'il existe essayons de retourner tous les quiz
        try {
            // recupérer la liste des quiz créer par l'utilisateur
            List<Quiz> quizList = quizService.findQuizByUsername(username);
            return  new ResponseEntity<>(quizList, HttpStatus.OK);
        }
        catch (Exception e){
            // Sinon si ça échoue
            return  new ResponseEntity<>("Une Erreur s'est produit lors de la liste des Quiz créer par l'utilisateur", HttpStatus.BAD_REQUEST);
        }
    }


    // Une méthode pour Enregister le quiz
    @PostMapping("/save")
    public ResponseEntity<?> saveQuiz(@RequestBody HashMap<String, String> request){
        // Extraire les données de user dans la requete pour traiter l'utlisateur
        // Recupérer son nom d'utilisateur s'il exite
        //String quizImageName = request.get("quizImageName");
        String username = request.get("username");
        String titre = request.get("titre");
        AppUser user = getAppUser(username);
        // Vérifier si l'utilisateur existe
        if (quizService.getQuizByTitre(titre) != null){
            // Si l'utlisateur est null, n'existe pas
            return  new ResponseEntity<>("Quiz existe déjà !", HttpStatus.CONFLICT);
        }
        if (user == null){
            // Si l'utlisateur est null, n'existe pas
            return  new ResponseEntity<>("Utilisateur n'existe pas !", HttpStatus.NOT_FOUND);
        }
        quizImageName = RandomStringUtils.randomAlphabetic(10);
        // Sinon si l'utilisateur existe alors il en ajouter un autre
        try {
            Quiz quiz = quizService.saveQuiz(user, request, quizImageName);
            // on l'enregistre
            return  new ResponseEntity<>(quiz, HttpStatus.CREATED);
        }catch (Exception e){
            // Si ça echoue
            return  new ResponseEntity<>("Une Erreur s'est produit lors de l'ajout de Quiz", HttpStatus.BAD_REQUEST);
        }
    }



    // Une méthode pour supprimer un Quiz
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable("id")Long id){
        // Récupérons le Quiz
        Quiz quiz = quizService.getQuizById(id);
        // Vérifier si le Quiz existe
        if (quiz == null){
            // Si le Quiz est null, n'existe pas
            return  new ResponseEntity<>("Quiz non trouver !", HttpStatus.NOT_FOUND);
        }
        // Sinon si le quiz existe, on peut essayé de le supprimer
        try{
            quizService.deleteQuiz(quiz);
            return new ResponseEntity<>("Quiz Supprimer !", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Erreur de suppression du Quiz !", HttpStatus.BAD_REQUEST);
        }
    }


    // Une méthode pour Assigner un Quiz à un Apprenant
    @PostMapping("/addQuizToStudent/{idQuiz}/{username}")
    public ResponseEntity<?> addQuizToStudent(@PathVariable Long idQuiz, @PathVariable String username){
        // Recupérons d'abord le quiz
        Quiz quiz = quizService.getQuizById(idQuiz);
        // Récupérons l'utilisateur aussi
        AppUser appUser = getAppUser(username);

        // vérifions si le quiz existe
        if (quiz == null){
            return new ResponseEntity<>("Quiz non trouver !", HttpStatus.NOT_FOUND);
        }
        // vérifions si l'utilisateur existe
        if (appUser == null){
            return new ResponseEntity<>("utilisateur non trouver !", HttpStatus.NOT_FOUND);
        }
        // sinon si Tout existe, Alors on l'affecte à l'utilisateur
        quizService.addQuizToUser(idQuiz, username);
        return new ResponseEntity<>("Quiz Affecter avec succes !", HttpStatus.OK);

    }


    // Une méthode pour ajouter une image à un quiz
    @PostMapping("/photoQuiz/upload")
     public ResponseEntity<String> fileUpload(HttpServletRequest request){
        try {
            quizService.saveQuizImage(request,quizImageName);
            return new ResponseEntity<>("Image ajouter !", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("image non Enregister !", HttpStatus.BAD_REQUEST);
        }
    }


    private AppUser getAppUser(String username) {
        return accountService.findByUsername(username);
    }






}
