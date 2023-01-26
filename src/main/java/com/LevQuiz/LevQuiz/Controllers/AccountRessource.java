package com.LevQuiz.LevQuiz.Controllers;

import com.LevQuiz.LevQuiz.Models.AppUser;
import com.LevQuiz.LevQuiz.Models.Quiz;
import com.LevQuiz.LevQuiz.Models.Role;
import com.LevQuiz.LevQuiz.Repositories.QuizRepository;
import com.LevQuiz.LevQuiz.Services.AccountService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController // Pour dire qu'il s'agit qu'il classe controller
@RequestMapping(value = "/user") // le path ou lien dans l'url
@NoArgsConstructor
public class AccountRessource {

    // Pour Encoder le mots de passe
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // Injectons l'interface AccountService
    @Autowired
    private AccountService accountService;

    // injectons le QuizRepository
    @Autowired
    private QuizRepository quizRepository;


    private Long userImageId;



    // Une méthode pour Lister les utilisateurs
    @GetMapping("/listUsers")
    public ResponseEntity<?> getUsersList(){
        List<AppUser> users = accountService.userList();
        // Vérifier si la liste est vide
        if (users.isEmpty()){
            // Vérifier si la liste est vide
            return new ResponseEntity<>("Auccun utilisateur trouver !",HttpStatus.OK);
        }
        // Sinon s'il y'a quelque choses on retourne la liste des utiilsateurs
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    // Une méthode pour trouver un seul utilisateur
    @GetMapping("/{username}")
    public ResponseEntity<?> getUserInfo(@PathVariable("username") String username){
        AppUser user = accountService.findByUsername(username);
        // Vérifier si l'utilisateur n'existe pas
        if (user == null){
            return new ResponseEntity<>("Utilisateur non trouver !",HttpStatus.NOT_FOUND);
        }
        // Sinon si on trouve l'utilisateur, on le retourne
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    // Une méthode qui va retourné la liste des utilisateur lors des Recherches
    @GetMapping("/findByUsername/{username}")
    public ResponseEntity<?> getUsersListByUsername(@PathVariable("username") String username){
        List<AppUser> users = accountService.getUserListByUsername(username);
        // Vérifier si la liste est vide
        if (users.isEmpty()){
            // Vérifier si la liste est vide
            return new ResponseEntity<>("Auccun utilisateur trouver !",HttpStatus.OK);
        }
        // Sinon s'il y'a quelque choses on retourne la liste des utiilsateurs
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    // Une méthode qui va Créer un utilisateur
    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody HashMap<String, String> request){ // RequestBody pour dire que les donnée se trouve dans le corps de la requete
        String firstname = request.get("firstname");
        String lastname = request.get("lastname");
        String username = request.get("username");
        String password = request.get("password");
        String email = request.get("email");

        // Vérifions si le non d'utilisateur existe déja
        if (accountService.findByUsername(username) != null){
            // si username existe,
            return new ResponseEntity<>("usernameExist",HttpStatus.CONFLICT);
        }
        // vérifier si email exist
        if (accountService.findByEmail(email) != null){
            // Si email exist
            return new ResponseEntity<>("emailExist",HttpStatus.CONFLICT);
        }
        // Essayons d'enregister l'utilisateur
        try {
            AppUser user = accountService.saveUser(firstname,lastname,username,password,email);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch (Exception e){
            // si ça échoue
            return new ResponseEntity<>("Une Erreur s'est produit lors d'enregistremet de user", HttpStatus.BAD_REQUEST);
        }
    }


    // Une méthode qui permet d'ajouter un User
    @PostMapping("/simpleAddUser")
    public AppUser simpleSaveUser(@RequestBody AppUser appUser){ // @RequestBody pour prendre les données de qui se trouve dans le Body
        return accountService.simpleSave(appUser);
    }


    // Une méthode pour mèttre l'utilisateur à jours
    @PostMapping("/update")
    public ResponseEntity<?> updateProfile(@RequestBody HashMap<String, String> request){
        // getter id user
        String id = request.get("id");
        // Cherchons l'utilisateur
        AppUser user = accountService.findUserById(Long.parseLong(id));
        // vérifier si l'utilisateur existe
        if (user == null){
            // Si l'utilisateur est null
            return new ResponseEntity<>("Utilisateur non trouvé !", HttpStatus.NOT_FOUND);
        }
        // Sinon si existe, alors on l'enregistre
        try {
            accountService.updateUser(user, request);
            userImageId = user.getId();
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Une Erreur s'est produit lors de mise en jours", HttpStatus.BAD_REQUEST);
        }
    }

    // Une méthode pour changer la photo de profil de l'utilisateur
    @PostMapping("/photo/upload")
    public ResponseEntity<String> fileUpload(@RequestParam("image") MultipartFile multipartFile) {
        try {
            accountService.saveUserImage(multipartFile, userImageId);
            return new ResponseEntity<>("Profil changer !", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Profil non changer !", HttpStatus.BAD_REQUEST);
        }
    }


    // une méthode pour changer le mots de passe
    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody HashMap<String, String> request ){
        // Recupérons le nom d'utilisateur dans la requête
        String username = request.get("username");
        // cherchons actuel utilisateur et passons le username récuperé
        AppUser appUser = accountService.findByUsername(username);
        // Vérifier si l'utisateur est null
        if (appUser == null){
            // Si l'utilisateur est null
            return new ResponseEntity<>("Utilisateur non trouvé !", HttpStatus.BAD_REQUEST);
        }
        // Sinon si l'utilisateur existe
        // Une variable pour récuperer actuel mots de passe de l'utilisateur
        String currentPassword = request.get("currentPassword");
        // une variable pour le nouveau mots de passe
        String newPassword = request.get("newPassword");
        // une variable pour la confirmation du nouveau mots de passe
        String confirmPassword = request.get("confirmPassword");
        // Vérifions si newPassword et confirmPassword sont différents
        if (!newPassword.equals(confirmPassword)){
            // Si les mots de passes sont différents
            return new ResponseEntity<>("PasswordNotMatched", HttpStatus.BAD_REQUEST);
        }
        // Sinon si les mots de passes sont identiques
        // Recupérons actuel mots de passe
        String userPassword = appUser.getPassword();
        // Essayons de changer le mots de passe
        try {
            // Vérifions si le nouveau mots de passe n'est pas vide
            if (!newPassword.isEmpty() && !StringUtils.isEmpty(newPassword)){
                // Conparons les mots de passe si actuel mots de passe et celui de la base de donnée sont les mêmes
                if (bCryptPasswordEncoder.matches(currentPassword, userPassword)){
                    // Si c'est les même
                     accountService.updateUserPassword(appUser, newPassword);
                } else {
                    return new ResponseEntity<>("IncorrectCurrentPassword", HttpStatus.BAD_REQUEST);
                }
                return new ResponseEntity<>("Mots passe changé avec succès !", HttpStatus.OK);
            }
        }
        catch (Exception e){
            return new ResponseEntity<>("Une Erreur s'est produit en changeant de mots de passe !", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Succes !", HttpStatus.OK);
    }


    // Une méthode pour le mots de passe oublié
    @GetMapping("/resetPassword/{email}")
    public ResponseEntity<String> resetPassword(@PathVariable("email") String email){
        // vérifions si l'utilisateur exite dansl la base grace à son email
        AppUser user = accountService.findByEmail(email);
        // Vérifier si l'utisateur est null
        if (user == null){
            // Si l'utilisateur est null
            return new ResponseEntity<>("emailNotFound", HttpStatus.BAD_REQUEST);
        }
        // sinon si l'utilisateur existe, il peut changé son mots de passe oublié
        accountService.resetPassword(user);
        return new ResponseEntity<>("Vérifié votre boîte mail !", HttpStatus.OK);
    }


    // une méthode qui va permettre de jouer à un Quiz
    @PostMapping(value = "/playQuiz/{iduser}/{idquiz}")
    public ResponseEntity<?> playQuiz(@PathVariable Long iduser, @PathVariable Long idquiz){
        // Recupérons l'utilisateur
        AppUser appUser = accountService.findUserById(iduser);
        // Recupérons le quiz
        Quiz quiz = quizRepository.findQuizById(idquiz);

        // vérifions si l'utilisateur existe
        if (appUser == null){
            return new ResponseEntity<>("Cet utilisateur n'existe pas !", HttpStatus.NOT_FOUND);
        }

        // vérifions si le quiz existe
        if (quiz == null){
            return new ResponseEntity<>("Quiz non trouver !", HttpStatus.NOT_FOUND);
        }
        accountService.playQuiz(iduser, idquiz);
        return new ResponseEntity<>("Jouer avec succes !", HttpStatus.OK);
    }

    // une méthode pour ajouter un Role
    @PostMapping("/role")
    public ResponseEntity<String> AddNewRole(@RequestBody Role role) {
        accountService.addNewRole(role);
        return new ResponseEntity<String>("Role Ajouté avec succès!", HttpStatus.OK);
    }


}
