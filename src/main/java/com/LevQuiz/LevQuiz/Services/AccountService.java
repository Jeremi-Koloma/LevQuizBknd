package com.LevQuiz.LevQuiz.Services;

import com.LevQuiz.LevQuiz.Models.AppUser;
import com.LevQuiz.LevQuiz.Models.Role;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

public interface AccountService {
    // Une methode qui va prendre l'utilisateur en parmètre pour l'Enregistrer
    AppUser saveUser(String firstname,String lastname, String username, String password, String email);

    // Une méthode qui va retourner un utilisateur par son Nom pour authentification
    AppUser findByUsername(String username);

    // Une méthode qui va retourner un utilisateur par son Email
    AppUser findByEmail(String email);

    // Une méthode qui va retourné une liste d'utilisateurs
    List<AppUser> userList();

    // Une méthode qui va retourné une liste des Apprenant
    List<Object> studentList();

    // Une méthode qui va retourné une liste des Formateurs
    List<Object> formateurList();

    // Une méthode qui va retourné une liste des Formateurs non Activer
    List<Object> formateurListNoActive();

    // Une méthode qui va retourné un Role d'utilisateur
    Role findUserRoleByName(String role);

    // une méthode qui permet d'ajouter les rôles dans la base de donnée
    Role addNewRole (Role role);

    // Une méthode de type void qui permet d'affecter un rôle à un user
    void addRoleToUser (String userName, String name);

    // Une méthode qui va retourné un Role avec actuel Role en paramètre pour l'Enregistrer
    Role saveRole(Role role);

    // Une méthode pour mèttre l'utilisateur à jours
    AppUser updateUser(AppUser user, HashMap<String, String> request);

    // Une méthode qui va retourné un utilisateur par son ID
    AppUser findUserById(Long id);

    // Une méthode va retourné String qui va prendre en param (idUser) à supprimer; // methode DELECTE
    String supprimer(Long idUser);

    // Une méthode pour Changer le mots de passe d'un utilisateur avec actuel utilisateur en paramètre
    void updateUserPassword(AppUser appUser, String newPassword);

    // Une méthode pour le mots de passe oublié
    void resetPassword(AppUser appUser);

    // Une méthode qui va retourné la liste des utilisateur par leurs Non d'utilisateur pour type barre de Recherche
    List<AppUser> getUserListByUsername(String username);

    // un simple savegarde des utilisateurs
    AppUser simpleSave(AppUser appUser);

    // une méthode qui va permettre de jouer à un quiz
    AppUser playQuiz(Long iduser, Long idquiz);

    // une méthode pour mèttre une photo de profil à jour
    String saveUserImage(MultipartFile multipartFile, Long userImageId);


}
