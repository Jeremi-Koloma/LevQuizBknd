package com.LevQuiz.LevQuiz.Services;

import com.LevQuiz.LevQuiz.Models.AppUser;
import com.LevQuiz.LevQuiz.Models.Role;

import java.util.HashMap;
import java.util.List;

public interface AccountService {
    // Une methode qui va prendre l'utilisateur en parmètre pour l'Enregistrer
    AppUser saveUser(String firstname, String lastname, String username, String email);

    // Une méthode qui va retourner un utilisateur par son Nom pour authentification
    AppUser findByUsername(String username);

    // Une méthode qui va retourner un utilisateur par son Email
    AppUser findByEmail(String email);

    // Une méthode qui va retourné une liste d'utilisateurs
    List<AppUser> userList();

    // Une méthode qui va retourné un Role d'utilisateur
    Role findUserRoleByName(String role);

    // Une méthode qui va retourné un Role avec actuel Role en paramètre pour l'Enregistrer
    Role saveRole(Role role);

    // Une méthode pour mèttre l'utilisateur à jours
    void updateUser(AppUser appUser, HashMap<String, String> request);

    // Une méthode qui va retourné un utilisateur par son ID
    AppUser findUserById(Long id);

    // Une méthode pour supprimer un utilisateur avec actuel utilisateur en paramètre
    void deleteUser(AppUser appUser);

    // Une méthode pour Changer le mots de passe d'un utilisateur avec actuel utilisateur en paramètre
    void updateUserPassword(AppUser appUser, String newPassword);

    // Une méthode pour le mots de passe oublié
    void resetPassword(AppUser appUser);

    // Une méthode qui va retourné la liste des utilisateur par leurs Non d'utilisateur pour type barre de Recherche
    List<AppUser> getUserListByUsername(String username);

    // un simple savegarde des utilisateurs
    void simpleSave(AppUser appUser);

}
