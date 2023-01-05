package com.LevQuiz.LevQuiz.Controllers;

import com.LevQuiz.LevQuiz.Models.AppUser;
import com.LevQuiz.LevQuiz.Services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController // Pour dire qu'il s'agit qu'il classe controller
@RequestMapping("/user") // le path ou lien dans l'url
@AllArgsConstructor // Pour l'injections des dépendances
public class AccountRessource {

    // Pour Encoder le mots de passe
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // Injectons l'interface AccountService
    private AccountService accountService;

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
            return new ResponseEntity<>("Utilisateur non trouver !",HttpStatus.OK);
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
        String email = request.get("email");
        // vérifier si email exist
        if (accountService.findByEmail(email) != null){
            // Si email exist
            return new ResponseEntity<>("Email Existe déjà !",HttpStatus.CONFLICT);
        }
        String username = request.get("username");
        // Essayons d'enregister l'utilisateur
        try {
            AppUser user = accountService.saveUser();
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("Une Erreur s'est produit lors d'enregistremet de user", HttpStatus.BAD_REQUEST);
        }
    }




}
